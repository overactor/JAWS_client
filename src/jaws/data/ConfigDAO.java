package jaws.data;

import java.util.Map;

import org.json.JSONObject;

/**
 * A DAO Interface that represents a connection to a JAWS server
 * 
 * @author Rik
 */
public interface ConfigDAO {
	
	/**
	 * The configurations get updated on the next update
	 */
	public void updateConfigs();
	
	/**
	 * Send configurations to the JAWS server
	 * 
	 * @param configs the configurations to save
	 */
	public void saveConfigs(Map<String, JSONObject> configs);

	public void restartServer();
}
