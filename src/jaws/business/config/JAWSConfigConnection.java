package jaws.business.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.json.JSONObject;

import jal.business.log.Log;
import jaws.data.ConfigDAO;
import jaws.data.ConfigLogDAO;

/**
 * Represents a connection to a JAWS server
 * 
 * @author Rik
 * 
 * @see jaws.data.config.ConfigDAO
 */
public class JAWSConfigConnection {
	
	ConfigDAO configDAO;
	Thread configDaoThread;
	
	public JAWSConfigConnection(String server, int port, Consumer<List<Log>> logCallback, Consumer<Config> configCallback) {
		ConfigLogDAO configLogDao = new ConfigLogDAO(server, port, 500,
		                             logList -> logCallback.accept(logList.stream().map(Log::from).collect(Collectors.toList())),
		                             configMap -> configCallback.accept(configFrom(configMap)));
		configDAO = configLogDao;
		configDaoThread = new Thread(configLogDao);
		configDaoThread.start();
	}
	
	/**
	 * Saves configurations to the JAWS server
	 * 
	 * @param config the configurations to save
	 */
	public void saveConfig(Config config) {		
		configDAO.saveConfigs(toJSONMap(config));
	}
	
	/**
	 * Loads configurations from the JAWS server
	 */
	public void loadConfigs() {
		configDAO.updateConfigs();
	}
	
	/**
	 * Restarts the JAWS server
	 */
	public void restartSever() {
		
	}
	
	/**
	 * Closes the connection to the JAWS server
	 */
	public void close() {
		
		configDaoThread.interrupt();
	}
	
	private Map<String, JSONObject> toJSONMap(Config config) {
		
		Map<String, JSONObject> configs = new HashMap<>();
		
		JSONObject webConfig = new JSONObject();
		webConfig.put("port", config.getPort());
		webConfig.put("webroot", config.getWebroot());
		webConfig.put("threads", config.getThreads());
		configs.put("web", webConfig);

		JSONObject logConfig = new JSONObject();
		logConfig.put("log_folder", config.getLogPath());
		configs.put("logs", logConfig);
		
		return configs;
	}
	
	private Config configFrom(Map<String, JSONObject> configs) {
		
		Config config = new Config();

		config.setPort(configs.get("web").getInt("port"));
		config.setWebroot(configs.get("web").getString("webroot"));
		config.setThreads(configs.get("web").getInt("threads"));
		
		config.setLogPath(configs.get("logs").getString("log_folder"));

		return config;
	}
}
