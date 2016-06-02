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
	
	private final Object updateConfigsLock = new Object();
	private volatile boolean updateConfigs;
	
	private Map<String, JSONObject> configs = new ConcurrentHashMap<>();

	public ConfigLogDAO(Socket server, int interval, Consumer<List<JSONObject>> logCallback, Consumer<Map<String, JSONObject>> configCallback) {
		this.server = server;
		this.interval = interval;
		this.logCallback = logCallback;
		this.configCallback = configCallback;
	}

	@Override
	public void updateConfigs() {
		synchronized (updateConfigsLock) {
			updateConfigs = true;
		}
	}

	@Override
	public void saveConfigs(Map<String, JSONObject> configs) {
		synchronized (this.configs) {
			this.configs.putAll(configs);
		}
	}

	@Override
	public void run() {
		while(true) {
			synchronized (updateConfigsLock) {
				// TODO stuff with updateConfigsLock
				this.updateConfigs = false;
			}
			synchronized (configs) {
				// TODO Do stuff with configs
			}
			// TODO write to and read from socket
		}
	}
}
