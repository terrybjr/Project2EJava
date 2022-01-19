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

import application.utils.DunGenLogger;
import application.cdi.annotations.GloballyManaged;
import application.cdi.beans.Global;

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
		String userId = "";
		//		for (Object object : invocationContext.getParameters()) {  // we might need the request back at some point, this is how we get it.
		//			if (object instanceof HttpServletRequest) {
		userId = this.securityContext != null ? this.securityContext.getUserPrincipal().getName() : "Anonymous";
		//						break;
		//			}
		//		}
		Object retObj = null;
		if (logger.isDebugEnabled()) {
			logger.debug(method + "setting userId: " + userId);
		}
		this.global.setUserId(userId);
		retObj = invocationContext.proceed();
		return retObj;
	}
}
