package application.data;

import org.apache.logging.log4j.Logger;

import application.utils.DunGenLogger;
import application.utils.MiscUtils;

/*
 * we use http status 200 for all ok
 * We use http status 400 for user errors 
 * we use http status 500 for other errors
 * you should not use other status code 
 */


public class StatusResp {
	public static Logger logger = DunGenLogger.getLogger();

	public static final int STAT_OK = 200;
	public static final int STAT_ACCEPTED = 202; // for async jobs
	public static final int STAT_USER = 400;
	public static final int STAT_FORBIDDEN = 403;
	public static final int STAT_INTERNAL = 500;


	private int httpStatusCd;
	// TODO: do we want a list of error messages?
	private String errMsg;
	private Object retObj; // only valid if status code is 200...

	/**
	 * Default constructor is the happy path...  Just returns STAT_OK with no msg or object.
	 */
	public StatusResp() {
		this.httpStatusCd = STAT_OK;
	}

	/**
	 * For when you only want a status passed back (no msg or object)
	 * @param httpStatusCd
	 */
	public StatusResp(final int httpStatusCd) {
		this.httpStatusCd = httpStatusCd;
		this.errMsg = "";
	}

	/**
	 * Normally used when the status is not OK and you want to return just a msg
	 * @param httpStatusCd
	 * @param errMsg
	 */
	public StatusResp(final int httpStatusCd, final String errMsg) {
		this.httpStatusCd = httpStatusCd;
		this.errMsg = errMsg;
	}

	/**
	 * If you do not want to use one of the other shorter ones and provide it all...
	 * @param httpStatusCd
	 * @param errMsg
	 * @param retObj
	 */
	public StatusResp(final int httpStatusCd, final String errMsg, final Object retObj) {
		this.httpStatusCd = httpStatusCd;
		this.errMsg = errMsg;
		this.retObj = retObj;
	}

	/**
	 * If you want no message and statusOK without having to pass them...
	 * kind of a quick good path with status and object.
	 * @param retObj
	 */
	public StatusResp(final Object retObj) {
		this.httpStatusCd = STAT_OK;
		this.errMsg = "";
		this.retObj = retObj;
	}

	/**
	 * helper to check just the status for STAT_OK, it does not check for presence of the object
	 * @return
	 * @see allOK()
	 */
	public boolean statusOK() {
		if (this.httpStatusCd == STAT_OK) {
			return true;
		}

		return false;
	}

	/**
	 * helper to check for both status as STAT_OK, and retObj is not null (have data).<br>
	 * Do not use this when you are not getting an object back...
	 * @return
	 */
	public boolean allOK() {
		if (this.httpStatusCd == STAT_OK && this.retObj != null) {
			return true;
		}
		return false;
	}

	public int getHttpStatusCd() {
		return this.httpStatusCd;
	}

	public void setHttpStatusCd(final int httpStatusCd) {
		this.httpStatusCd = httpStatusCd;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

	public void setErrMsg(final String errMsg) {
		if (MiscUtils.hasValue(errMsg)) {
			if (logger.isDebugEnabled()) {
				logger.debug(errMsg);
			}
		}
		this.errMsg = errMsg;
	}

	public Object getRetObj() {
		return this.retObj;
	}

	public void setRetObj(final Object retObj) {
		this.retObj = retObj;
	}

	@Override
	public String toString() {
		return MiscUtils.objToJson(this);
	}
}

