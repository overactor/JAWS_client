package jaws.data;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.json.JSONObject;

public class ConfigLogDAO implements ConfigDAO, Runnable {

	private final Socket server;

	private final int interval;
	private final Consumer<List<JSONObject>> logCallback;
	private final Consumer<Map<String, JSONObject>> configCallback;

	private volatile Boolean updateConfigs;

	private Map<String, JSONObject> configsToSave = new ConcurrentHashMap<>();

	public ConfigLogDAO(Socket server, int interval, Consumer<List<JSONObject>> logCallback, Consumer<Map<String, JSONObject>> configCallback) {
		this.server = server;
		this.interval = interval;
		this.logCallback = logCallback;
		this.configCallback = configCallback;
	}

	@Override
	public void updateConfigs() {
		synchronized (updateConfigs) {
			updateConfigs = true;
		}
	}

	@Override
	public void saveConfigs(Map<String, JSONObject> configs) {
		synchronized (this.configsToSave) {
			this.configsToSave.putAll(configs);
		}
	}

	@Override
	public void run() {

		long lastLogUpdate = -1;

		while(true) {

			JSONObject requestJson = new JSONObject();

			JSONObject updateLogsJson = new JSONObject();
			requestJson.put("updateLogs", updateLogsJson);

			if(lastLogUpdate > 0) {
				updateLogsJson.put("lastUpdate", lastLogUpdate);
			}

			synchronized (updateConfigs) {
				// TODO stuff with updateConfigsLock
				if(updateConfigs) {
					JSONObject updateConfigsJson = new JSONObject();
					requestJson.put("updateConfigs", updateConfigsJson);

					updateConfigs = false;
				}
			}
			synchronized (configsToSave) {
				// TODO Do stuff with configs
			}
			// TODO write to and read from socket
		}
	}
}
