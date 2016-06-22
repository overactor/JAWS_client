package jaws.data;

import java.util.Map;

import org.json.JSONObject;

public interface ConfigDAO {

	public void updateConfigs();

	public void saveConfigs(Map<String, JSONObject> configs);

	public void restartServer();
}
