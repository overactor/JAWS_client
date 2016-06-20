package jaws.presentation.controllers;

import static trycrash.Try.tryCatch;

import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import jal.business.log.Log;
import jaws.business.config.Config;
import jaws.business.config.JAWSConfigConnection;
import jaws.business.presets.Preset;
import jaws.business.presets.PresetFactory;
import jaws.presentation.models.FilteredListModel;
import jaws.presentation.views.ConfigView;

/**
 * A controller class for the JAWS config-client
 * 
 * @author Rik
 */
public class ConfigController {
	
	private ConfigView configView;
	
	private FilteredListModel<Log, String> logsModel;
	SpinnerNumberModel httpPortModel, threadModel;
	
	private JAWSConfigConnection configConnection;
	
	public ConfigController() {
		
		logsModel = new FilteredListModel<>(Log::toString);		
		httpPortModel = new SpinnerNumberModel(80, 1, 65536, 1);
		threadModel = new SpinnerNumberModel(10, 1, 9999, 1);
		
		PresetDelegate presetDelegate = new PresetDelegate() {
			
			@Override
			public void savePresetClicked() {
				
				Preset preset = ConfigController.this.presetFromGUI();
				PresetFactory.savePreset(preset);
			}
			
			@Override
			public void deletePresetClicked() {
				PresetFactory.deletePreset(ConfigController.this.configView.getPresetName());
			}
			
			@Override
			public void loadPresetClicked() {
				PresetFactory.loadPreset(ConfigController.this.configView.getPresetName())
				             .ifPresent(ConfigController.this::setPreset);
			}
		};
		
		ConfigDelegate configDelegate = new ConfigDelegate() {
			
			@Override
			public void resetClicked() {

				
			}
			
			@Override
			public void onOffClicked() {

				
			}
			
			@Override
			public void applyClicked() {

				Config configToSave = new Config();
				configToSave.setLogPath(configView.getLogPath());
				configToSave.setPort((int) httpPortModel.getValue());
				configToSave.setThreads((int) threadModel.getValue());
				configToSave.setWebroot(configView.getWebroot());
				
				configConnection.saveConfig(configToSave);
			}
		};
		
		MenuDelegate menuDelegate = new MenuDelegate() {
			
			@Override
			public void importClicked() {

				PresetFactory.importPreset()
	                         .ifPresent(ConfigController.this::setPreset);
			}
			
			@Override
			public void exportClicked() {
				PresetFactory.exportPreset(ConfigController.this.presetFromGUI());
			}
			
			@Override
			public void connectClicked() {

				String host = (String) JOptionPane.showInputDialog(configView,
				                                                   "Enter the server to connect to (hostname:port)",
				                                                   "Connect...",
				                                                   JOptionPane.PLAIN_MESSAGE);
				String hostname = "";
				int port = 8080;
				
				if(host.contains(":")) {
					hostname = host.split(":")[0];
					port = tryCatch(() -> Integer.parseInt(host.split(":")[1])).orElse(8080);
				}
				
				configConnection = new JAWSConfigConnection(hostname, port,
				                                            logs -> logs.forEach(logsModel::add),
				                                            config -> SwingUtilities.invokeLater(() -> {
				                                            	configView.setLogPath(config.getLogPath());
				                                            	httpPortModel.setValue(config.getPort());
				                                            	threadModel.setValue(config.getThreads());
				                                            	configView.setWebroot(config.getWebroot());
				                                            }));
			}
			
			@Override
			public void disconnectClicked() {
				
				if(configConnection != null)
					configConnection.close();
				configConnection = null;
			}
		};
		
		logsModel.setPredicate(log -> log.getLogLevel().getLevel() >= configView.getLogLevel().getLevel()
		                              &&  Arrays.stream(configView.getLogTags().split(","))
		                                        .map(String::toLowerCase)
		                                        .anyMatch(tag -> tag.equals(log.getTag()))
		);
		
		configView = new ConfigView(presetDelegate, configDelegate, menuDelegate,
		                            logsModel, logsModel::refresh, httpPortModel, threadModel);
	}
	
	private Preset presetFromGUI() {
		
		Preset preset = PresetFactory.createPreset(configView.getPresetName());
		// TODO: set preset stuff.
		preset.setConfig(configFromGUI());
		
		return preset;
	}
	
	private Config configFromGUI() {
		
		Config config = new Config();
		
		config.setWebroot(configView.getWebroot());
		config.setLogPath(configView.getLogPath());
		config.setPort(httpPortModel.getNumber().intValue());
		config.setThreads(threadModel.getNumber().intValue());
		
		return config;
	}
	
	private void setPreset(Preset preset) {

		// TODO: set preset stuff
		setConfig(preset.getConfig());
	}
	
	private void setConfig(Config config) {

		configView.setWebroot(config.getWebroot());
		configView.setLogPath(config.getLogPath());
		httpPortModel.setValue(config.getPort());
		threadModel.setValue(config.getThreads());
	}
}
