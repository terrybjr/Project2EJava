package application.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.Logger;

import application.data.StaticDataInterface;
import application.data.StatusResp;
import application.session.StaticDataSLS;
import application.utils.DunGenLogger;
import application.utils.MiscUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path(value = "data")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(tags = { "Static Data Rest" })
public class StaticDataRest {
	static Logger logger = DunGenLogger.getLogger();
	String className = this.getClass().getSimpleName();

	@EJB
	StaticDataSLS staticDataSLS;

	@GET
	@Path("getStaticData/{value}")
	@ApiOperation(value = "Get Static Data")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success") })
	public Response getStaticData(
			@PathParam("value") final String value,
			@Context final HttpServletRequest request,
			@Context final HttpServletResponse response) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		List<? extends StaticDataInterface> staticData;
		try {
			staticData = this.staticDataSLS.getStaticData(value);
			if (logger.isDebugEnabled()) {
				logger.debug(method + "staticData: " + staticData);
			}
			return MiscUtils.buildResponse(new StatusResp(staticData));
		} catch (Exception ex) {
			return MiscUtils.buildResponse(ex);
		}
	}
}
