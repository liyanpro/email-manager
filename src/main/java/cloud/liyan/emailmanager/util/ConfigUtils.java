package cloud.liyan.emailmanager.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
/**
 * @author liyan
 * @description
 * @date 2019-09-07 11:20
 */
public class ConfigUtils {

	private static Configuration conf;
	static {
		try {
			conf = new PropertiesConfiguration("email.properties");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static Configuration getConf() {
		return conf;
	}

	public static void setConf(Configuration conf) {
		ConfigUtils.conf = conf;
	}

}
