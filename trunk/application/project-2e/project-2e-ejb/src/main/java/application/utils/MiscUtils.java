package application.utils;

import java.lang.reflect.Method;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.Logger;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;

import application.data.StatusResp;
import application.entity.User;

/**
 * Credit goes to Joseph Witherspoon for the majority of the utils in this file.
 *
 */
public class MiscUtils {

	private static String className = "MiscUtils";

	static Logger logger = DunGenLogger.getLogger();
	public static boolean hasValue(final String val) {
		if (val == null || val.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public static String getParmString(final String parm) {
		if (parm != null) {
			return parm.trim();
		} else {
			return "";
		}
	}

	public static Method matchMethod(final Method[] list, final String methodName) {
		if (methodName == null) {
			return null;
		}
		for (Method meth : list) {
			if (meth.getName().equalsIgnoreCase(methodName)) {
				return meth;
			}
		}
		return null;

	}

	/**
	 * This uses Jackson code to convert an object to a json string. We often use
	 * this in our toString() method override. <br>
	 * Example use:
	 * 
	 * <pre>
	 *  {@literal @}Override
	 *  public String toString() {
	 *   return MiscUtils.objToJson(this);
	 *  }
	 * </pre>
	 * 
	 * @param obj instance to convert to a json string
	 * @return json representation in a single line
	 */
	public static String objToJson(final Object obj) {
		return objToJson(obj, SINGLE_LINE);
	}

	/**
	 * Use SINGLE_LINE instead of a simple true/false, MiscUtils.SINGLE_LINE or
	 * !MiscUtils.SINGLE_LINE
	 */
	public static final boolean SINGLE_LINE = true;

	/**
	 * Converts an object to a json string. This version allows pretty format that
	 * spreads it across multiple lines. This is not very friendly for our logs...
	 * when you grep our logs with multiple json lines you will find just tiny
	 * fragments out of context. For logging we should use single line format and
	 * reformat in the editor.
	 * 
	 * @param obj        object to convert to a json string
	 * @param singleLine if true, all output is on a single line. If false, output
	 *                   is pretty. Use the SINGLE_LINE constant in your calling
	 *                   code!
	 * @return json representation of the object
	 */
	public static String objToJson(final Object obj, final boolean singleLine) {
		if (obj == null) {
			return "";
		}
		String json = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			if (singleLine) {
				json = mapper.writeValueAsString(obj);
			} else {
				json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			}
		} catch (Exception e) { // we need to catch any error, do not want to fail anything here
			logger.warn("objToJson failed converting " + obj.getClass().getName());
			logger.warn("objToJson type " + obj.getClass().getTypeName());
			logger.warn("objToJson: " + e); // do not need complete stack...
			return "Error";
		}
		return json;
	}

	/**
	 * Adds Auth Header if neccessary
	 * 
	 * @param valResp
	 * @return
	 */
	public static Response buildResponse(final StatusResp valResp, final String token) {
		return buildResponse(valResp, false, token);
	}

	/**
	 * Builds a Response based on the error type. It will also return a message if
	 * it exist.
	 * 
	 * @param valResp
	 * @return
	 */
	public static Response buildResponse(final StatusResp valResp) {
		return buildResponse(valResp, false, null);
	}

	/**
	 * This is set to true to prevent calling ObjToJson if it cannot handle the obj.
	 * one example is DocsResource which passes in a stream!
	 */
	public static final boolean SKIP_LOG_OBJ = true;

	public static Response buildResponse(final StatusResp valResp, final boolean skipLogObj, final String token) {
		final String method = className + ".buildResponse(StatusResp)";
		if (logger.isDebugEnabled()) {
			if (skipLogObj) {
				String objType = (valResp.getRetObj() == null) ? "null" : valResp.getRetObj().getClass().getName();
				if (logger.isDebugEnabled()) {
					logger.debug(method + "status=" + valResp.getHttpStatusCd() + "  objType=" + objType + "  msg="
							+ valResp.getErrMsg());
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug(method + " valResp = " + valResp);
				}
			}
		}
		if (valResp.getHttpStatusCd() != StatusResp.STAT_OK) {
			if (MiscUtils.hasValue(valResp.getErrMsg())) {
				return Response.status(valResp.getHttpStatusCd()).entity(valResp.getErrMsg()).build();
			}
			return Response.status(valResp.getHttpStatusCd()).build();
		}
		// returning a message if it exist... may be informative...
		if (MiscUtils.hasValue(valResp.getErrMsg())) {
			return Response.status(valResp.getHttpStatusCd()).entity(valResp.getErrMsg()).build();
		}
		if (token != null) {
			return Response.ok(valResp.getRetObj()).header(HttpHeaders.AUTHORIZATION, "Bearer" + token).build();
		}
		return Response.ok(valResp.getRetObj()).build();
	}

	/**
	 * Builds a response with the given status code.
	 * 
	 * @param code
	 * @return
	 */
	public static Response buildResponse(final int code) {
		return Response.status(code).build();
	}

	/**
	 * Since there is no StatusRep for exceptions, this allows us to pass back an
	 * error message to the front end if we throw an ErrorMessageException No way to
	 * control the http status, so using Internal error...
	 * 
	 * @param ex
	 * @return
	 */
	public static Response buildResponse(final Exception ex) {
		// create a list of all exceptions by getting the cause, this will allow
		// us to search if we got an exception with a specific string
		List<Throwable> trList = new ArrayList<>();
		trList.add(ex);
		Throwable tr = ex.getCause();
		while (tr != null) {
			trList.add(tr);
			tr = tr.getCause();
		}
		if (logger.isDebugEnabled()) {
			for (Throwable tr2 : trList) {
				if (logger.isDebugEnabled()) {
					logger.debug("buildResponse(ex) trList =" + tr2.toString());
				}
			}
		}

		// check first for out application exception with a msg
		Throwable match = findTrContains(trList, "ErrorMessageException");
		if (match != null) {
			String errMsg = match.getMessage();
			return MiscUtils.buildResponse(new StatusResp(StatusResp.STAT_INTERNAL, errMsg));
		}
		//		match = findTrContains(trList, "EJBAccessException");
		//		if (match != null) {
		//			return MiscUtils.buildResponse(new StatusResp(StatusResp.STAT_FORBIDDEN, StatusResp.ACCESS_DENIED));
		//		}
		//		match = findTrContains(trList, "Connection refused");
		//		if (match != null) {
		//			return MiscUtils.buildResponse(new StatusResp(StatusResp.STAT_INTERNAL, "Connection refused"));
		//		}
		//
		//		match = findTrContains(trList, "The Session is closed");
		//		if (match != null) {
		//			return MiscUtils.buildResponse(new StatusResp(StatusResp.STAT_INTERNAL, "The Session is closed"));
		//		}

		if (logger.isDebugEnabled()) {
			logger.debug("buildResponse returning the message...  could not refine it further");
		}
		return MiscUtils.buildResponse(new StatusResp(StatusResp.STAT_INTERNAL, ex.getMessage()));
	}

	public static Throwable findTrContains(
			final List<Throwable> list,
			final String value)
	{
		for (Throwable tr : list) {
			if (tr.toString().contains(value)) {
				return tr;
			}
		}
		return null;
	}

	public static Map<String, String> hashPassword(final String password) {
		org.apache.shiro.util.ByteSource salt = MiscUtils.getSalt();

		Map<String, String> credMap = new HashMap<String, String>();
		credMap.put("hashedPassword", MiscUtils.hashAndSaltPassword(password, salt));
		credMap.put("salt", salt.toHex());
		return credMap;
	}

	public static String hashAndSaltPassword(final String password, final org.apache.shiro.util.ByteSource salt) {
		return new Sha512Hash(password, salt, 2000000).toHex();
	}

	public static org.apache.shiro.util.ByteSource getSalt() {
		return new SecureRandomNumberGenerator().nextBytes();
	}

	public static boolean authenticateUser(final User user, final String password) {
		ByteSource salt = ByteSource.Util.bytes(Hex.decode(user.getSalt()));
		String hashedPassword = hashAndSaltPassword(password, salt);
		return hashedPassword.equals(user.getPassword());
	}

	public static Key generateKey(final String keyString) {
		return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
	}

}
