package jaws.data;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.json.JSONObject;

public class ConfigLogDAO implements ConfigDAO, Runnable {

	private final Socket server;

	private final int interval;
	private final Consumer<List<JSONObject>> logCallback;
	private final Consumer<Map<String, JSONObject>> configCallback;

	private boolean updateConfigs;

	public ConfigLogDAO(Socket server, int interval, Consumer<List<JSONObject>> logCallback, Consumer<Map<String, JSONObject>> configCallback) {
		this.server = server;
		this.interval = interval;
		this.logCallback = logCallback;
		this.configCallback = configCallback;
	}

	@Override
	public void updateConfigs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveConfigs(Map<String, JSONObject> configs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
