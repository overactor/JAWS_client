package jaws.business.presets;


/**
 * A class to represent saved config presets for JAWS
 * 
 * @author Rik
 */
public class Preset {
	
	String name;
	
	String webroot;
	int port;
	int threads;
	
	String logPath;
	String logTags;
	int logLevel;
	
	Preset(String name) {
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
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
	 * @return the logTags
	 */
	public String getLogTags() {
		return logTags;
	}
	
	/**
	 * @param logTags the logTags to set
	 */
	public void setLogTags(String logTags) {
		this.logTags = logTags;
	}
	
	/**
	 * @return the logLevel
	 */
	public int getLogLevel() {
		return logLevel;
	}
	
	/**
	 * @param logLevel the logLevel to set
	 */
	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}
}
