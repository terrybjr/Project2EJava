package application.cdi.interceptors;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.logging.log4j.Logger;

import application.utils.DunGenLogger;
import application.utils.MiscUtils;
import application.cdi.annotations.RestErrorHandled;
import application.cdi.beans.Global;

@RestErrorHandled
@Interceptor
@Priority(1)
public class RestErrorHandlerInterceptor {
	@Inject
	Global global;
	@Context
	private SecurityContext securityContext;

	static Logger logger = DunGenLogger.getLogger();

	/**
	 * This interceptor acts as a first priority interceptor, if an error occurs within the method, this interceptor will "Handle it" and return a sanatized error to the front end. This is meant for resource methods only. NOT FOR BUSINESS LOGIC USE
	 * @param invocationContext
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object handleErrorIfExists(final InvocationContext invocationContext) {
		String method = this.getClass().getSimpleName() + "." + new Throwable().getStackTrace()[0]
				.getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "entering");
		}
		HttpServletRequest request = null;
		for (Object object : invocationContext.getParameters()) {
			if (object instanceof HttpServletRequest) {
				request = (HttpServletRequest) object;
				break;
			}
		}
		try {
			Object obj = invocationContext.proceed();
			if (obj instanceof Response) {
				Response resp = (Response) obj;
				if (!(resp.getStatus() == 200)) {
					logger.warn(method + "Non 200 response occured printing request details.");
					Object[] array = invocationContext.getParameters();
					logger.warn(method + " request submitted by user: "
							+ ((this.securityContext != null) ? this.securityContext.getUserPrincipal().getName()
									: "Anonymous"));
					//					logger.warn(method + " request url: " + request.getRequestURL().toString());
					for (Object object : array) {
						logger.warn(method + " request argument: " + object);
					}
					logger.warn(method + "response code: " + resp.getStatus());
					logger.warn(method + "response body: " + resp.getEntity());
				}
			}
			return obj;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(method + "Exception occured printing request details.");
			Object[] array = invocationContext.getParameters();
			logger.error(method + " request submitted by user: " + ((this.securityContext != null)
					? 
							this.securityContext.getUserPrincipal().getName() : "Anonymous"));
			//			logger.error(method + " request url: " + request.getRequestURL().toString());
			for (Object object : array) {
				logger.error(method + " request argument: " + object);
			}
			logger.error(method + "Exception Stack Trace.");
			logger.error(method + " Exception (" + invocationContext.getMethod().getName() + "): ", e);
			return MiscUtils.buildResponse(e);
		}
	}
}