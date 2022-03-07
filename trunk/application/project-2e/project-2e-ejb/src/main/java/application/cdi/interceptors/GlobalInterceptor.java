package application.cdi.interceptors;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.apache.logging.log4j.Logger;

import application.cdi.annotations.GloballyManaged;
import application.cdi.beans.Global;
import application.utils.DunGenLogger;

@GloballyManaged
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class GlobalInterceptor {
	static Logger logger = DunGenLogger.getLogger();
	@Inject
	Global global;
	@Context
	private SecurityContext securityContext;

	@AroundInvoke
	public Object setGlobals(final InvocationContext invocationContext) throws Exception {
		String method = this.getClass().getSimpleName() + ".setGlobals: ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		String userId = "Anonymous";
		for (Object object : invocationContext.getParameters()) {
			if (object instanceof HttpServletRequest) {
				userId = ((HttpServletRequest) object).getRemoteUser();
			}
		}
		Object retObj = null;
		this.global.setUserId(userId);
		if (logger.isDebugEnabled()) {
			logger.debug(method + "setting userId: " + this.global.getUserId());
		}
		retObj = invocationContext.proceed();
		return retObj;
	}
}
