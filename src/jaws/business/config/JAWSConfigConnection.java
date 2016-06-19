package jaws.business.config;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.json.JSONObject;

import jaws.data.ConfigDAO;
import jaws.data.ConfigLogDAO;

public class JAWSConfigConnection {
	
	ConfigDAO configDao;
	
	JAWSConfigConnection(String server, int port, Consumer<List<JSONObject>> logCallback, Consumer<Map<String, JSONObject>> configCallback) {
		configDao = new ConfigLogDAO(server, port, 500, logCallback, configCallback);
	}
}
