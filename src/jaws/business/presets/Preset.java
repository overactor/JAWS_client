package jaws.business.presets;

import jaws.business.config.Config;

/**
 * A class to represent saved config presets for JAWS
 * 
 * @author Rik
 */
public class Preset {
	
	String name;
	
	Config config;
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
	 * @return the config
	 */
	public Config getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(Config config) {
		this.config = config;
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
