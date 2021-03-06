package jaws.business.presets;

import org.json.JSONObject;

import jal.business.log.LogLevel;
import jaws.business.config.Config;

/**
 * A class to represent saved config presets for JAWS
 * 
 * @author Rik
 */
public class Preset {
	
	private String name;
	
	private Config config;
	private String logTags;
	private LogLevel logLevel;
	
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
	public LogLevel getLogLevel() {
		return logLevel;
	}
	
	/**
	 * @param logLevel the logLevel to set
	 */
	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}
	
	/**
	 * @return the preset as a JSON Object
	 */
	public JSONObject toJSON() {
		
		return new JSONObject().put("name", name)
				               .put("config", config.toJSON())
		                       .put("logLevel", logLevel.getLevel())
		                       .put("logTags", logTags);
	}
}
