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

public class JAWSConfigConnection {
	
	ConfigDAO configDao;
	Thread configDaoThread;
	
	public JAWSConfigConnection(String server, int port, Consumer<List<Log>> logCallback, Consumer<Config> configCallback) {
		ConfigLogDAO configLogDao = new ConfigLogDAO(server, port, 500,
		                             logList -> logCallback.accept(logList.stream().map(Log::from).collect(Collectors.toList())),
		                             configMap -> configCallback.accept(configFrom(configMap)));
		configDao = configLogDao;
		configDaoThread = new Thread(configLogDao);
		configDaoThread.run();
	}
	
	public void saveConfig(Config config) {		
		configDao.saveConfigs(toJSONMap(config));
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
