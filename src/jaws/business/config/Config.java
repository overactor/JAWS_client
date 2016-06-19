package jaws.business.config;

public class Config {
	
	String webroot;
	int port;
	int threads;
	
	String logPath;
	
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
}
