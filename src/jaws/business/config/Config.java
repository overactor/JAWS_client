package jaws.business.config;

import org.json.JSONObject;

public class Config {
	
	private String webroot;
	private int port;
	private int threads;
	
	private String logPath;
	
	/**
	 * @return the webroot
	 */
	public String getWebroot() {
		return webroot;
	}
	
	/**
	 * @param webroot the webroot to set
	 */
	public void setWebroot(String webroot) {
		this.webroot = webroot;
	}
	
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * @return the threads
	 */
	public int getThreads() {
		return threads;
	}
	
	/**
	 * @param threads the threads to set
	 */
	public void setThreads(int threads) {
		this.threads = threads;
	}
	
	/**
	 * @return the logPath
	 */
	public String getLogPath() {
		return logPath;
	}
	
	/**
	 * @param logPath the logPath to set
	 */
	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
	
	/**
	 * @return the config as a JSON Object
	 */
	public JSONObject toJSON() {
		
		JSONObject json = new JSONObject();
		
		json.put("webroot", webroot);
		json.put("port", port);
		json.put("threads", threads);
		json.put("logPath", logPath);
		
		return json;
	}
	
	/**
	 * @param json the JSON Object to parse
	 * 
	 * @return the Config represented by the JSON Object
	 */
	public static Config from(JSONObject json) {
		
		Config config = new Config();
		
		config.webroot = json.getString("webroot");
		config.port = json.getInt("port");
		config.threads = json.getInt("threads");
		config.logPath = json.getString("logPath");
		
		return config;
	}
}
