package application.utils;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DunGenLogger {
	public static Logger logger = null;

	public static Logger getLogger() {
		if (logger == null) {
			logger = LogManager.getLogger("DunGen");
		}
		return logger;
	}
}
