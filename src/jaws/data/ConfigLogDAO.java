package jaws.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

public class ConfigLogDAO implements ConfigDAO, Runnable {

	private final String server;
	private final int port;

	private final int interval;
	private final Consumer<List<JSONObject>> logCallback;
	private final Consumer<Map<String, JSONObject>> configCallback;

	private volatile Boolean updateConfigs;

	private Map<String, JSONObject> configsToSave = new ConcurrentHashMap<>();

	public ConfigLogDAO(String server, int port, int interval, Consumer<List<JSONObject>> logCallback, Consumer<Map<String, JSONObject>> configCallback) {
		this.server = server;
		this.port = port;
		this.interval = interval;
		this.logCallback = logCallback;
		this.configCallback = configCallback;
		updateConfigs = false;
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
			Socket connection;
			try {
				connection = new Socket(server, port);
			
				long lastStartingTime = System.currentTimeMillis();
	
				JSONObject requestJson = new JSONObject();
	
				JSONObject updateLogsJson = new JSONObject();
				requestJson.put("updateLogs", updateLogsJson);
	
				if(lastLogUpdate > 0) {
					updateLogsJson.put("lastUpdate", lastLogUpdate);
				}
	
				synchronized (updateConfigs) {
					if(updateConfigs) {
						JSONObject updateConfigsJson = new JSONObject();
						requestJson.put("updateConfigs", updateConfigsJson);
	
						updateConfigs = false;
					}
				}
				synchronized (configsToSave) {
					if(configsToSave.size() > 0) {
						JSONObject configsToSaveJson = new JSONObject();
						requestJson.put("saveConfigs", configsToSaveJson);
						
						for(Entry<String, JSONObject> configToSaveJson : configsToSave.entrySet()) {
							configsToSaveJson.put(configToSaveJson.getKey(), configToSaveJson.getValue());
						}
						
						configsToSave.clear();
					}
				}
				try {
					OutputStream out = connection.getOutputStream();
					out.write(requestJson.toString().getBytes());
					out.write("\nEndOfMessage\n".getBytes());
					
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					StringBuilder stringBuilder = new StringBuilder();
					{
						String line;
						while((line = in.readLine()) != null && !line.equals("EndOfMessage")) {
							stringBuilder.append(line);
						}
					}
					JSONObject response = new JSONObject(stringBuilder.toString());
					
					if(response.has("logUpdate")) {
						JSONObject logUpdate = response.getJSONObject("logUpdate");
						lastLogUpdate = logUpdate.getLong("lastUpdate");
						JSONArray logsJson = logUpdate.getJSONArray("logs");
						List<JSONObject> logs = new ArrayList<>();
						for(int i=0; i<logsJson.length(); i++) {
							logs.add(logsJson.getJSONObject(i));
						}
						
						logCallback.accept(logs);
					}
					
					if(response.has("configUpdate")) {
						JSONObject configUpdate = response.getJSONObject("configUpdate");
						Map<String, JSONObject> newConfigs = new HashMap<>();
						Iterator<String> keys = configUpdate.keys();
						while(keys.hasNext()) {
							String key = keys.next();
							JSONObject log = configUpdate.getJSONObject(key);
							newConfigs.put(key, log);
						}
						
						configCallback.accept(newConfigs);
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				
				try {
					Thread.sleep(Math.max(interval - (System.currentTimeMillis() - lastStartingTime), 0));
				} catch (InterruptedException e) {
					break;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
