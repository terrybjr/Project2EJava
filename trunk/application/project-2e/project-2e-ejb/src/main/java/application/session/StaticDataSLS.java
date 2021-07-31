package application.session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.logging.log4j.Logger;

import application.data.StaticData;
import application.entity.ref.RefAlignment;
import application.entity.ref.RefAncestry;
import application.entity.ref.RefLanguage;
import application.entity.ref.RefSize;
import application.entity.ref.data.RefAncestryData;
import application.utils.DunGenLogger;
import application.utils.Lookup;

@Stateless
@EJB(name = "java:global/StaticDataSLS", beanInterface = StaticDataSLS.class)
@LocalBean
public class StaticDataSLS extends SLSBase {
	static Logger logger = DunGenLogger.getLogger();
	String className = this.getClass().getSimpleName();

	@EJB
	PersistenceSLS persistenceSLS;

	public List<? extends StaticData> getStaticData(final String value) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		final Map<String, Integer> parameters = new HashMap<>();
		switch (value) {
		case Lookup.STATIC_DATA_ANCESTRY:
			List<RefAncestry> ancestryList = this.persistenceSLS.getDataList(RefAncestry.QUERY_BY_ALL_ABILITY_BOOST,
					RefAncestry.class, parameters, method);
			ancestryList = this.persistenceSLS.getDataList(RefAncestry.QUERY_BY_ALL_ABILITY_FLAW, RefAncestry.class,
					parameters, method);
			ancestryList = this.persistenceSLS.getDataList(RefAncestry.QUERY_BY_ALL_LANGUAGES, RefAncestry.class,
					parameters, method);
			return ancestryList;
		case Lookup.STATIC_DATA_ALIGNMENT:
			return this.persistenceSLS.getDataList(RefAlignment.QUERY_BY_ALL, RefAlignment.class, parameters, method);
		case Lookup.STATIC_DATA_SIZE:
			return this.persistenceSLS.getDataList(RefSize.QUERY_BY_ALL, RefSize.class, parameters, method);
		case Lookup.STATIC_DATA_LANGUAGE:
			return this.persistenceSLS.getDataList(RefLanguage.QUERY_BY_ALL, RefLanguage.class, parameters, method);
		}
		return null;
	}

	public StaticData getDataObject(final String value, final String key) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		StaticData data = null;
		final Map<String, String> parameters = new HashMap<>();
		switch (value) {
		case Lookup.STATIC_DATA_ANCESTRY:
			parameters.put("name", key);
			data = this.persistenceSLS
					.getData(RefAncestryData.QUERY_BY_ANCESTRY_NAME, RefAncestryData.class, parameters, method).get();
			break;
		}
		if (data != null) {
			data.toString();
		}
		return data;
	}
}
