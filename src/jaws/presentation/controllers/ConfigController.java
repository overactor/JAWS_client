package jaws.presentation.controllers;

import static trycrash.Try.tryCatch;

import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

import jal.business.log.Log;
import jaws.business.config.Config;
import jaws.business.presets.Preset;
import jaws.business.presets.PresetFactory;
import jaws.data.ConfigLogDAO;
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
	
	private ConfigLogDAO configLogDAO;
	private Thread configLogDAOThread;
	
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
				ConfigController.this.setPreset(PresetFactory.loadPreset(ConfigController.this.configView.getPresetName()));
			}
		};
		
		ConfigDelegate configDelegate = new ConfigDelegate() {
			
			@Override
			public void resetClicked() {

				if(configLogDAO != null)
					configLogDAO.updateConfigs();
			}
			
			@Override
			public void onOffClicked() {

				
			}
			
			@Override
			public void applyClicked() {

				
			}
		};
		
		MenuDelegate menuDelegate = new MenuDelegate() {
			
			@Override
			public void importClicked() {

				
			}
			
			@Override
			public void exportClicked() {

				
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
				
				configLogDAO = new ConfigLogDAO(hostname, port, 5, null, null);
				configLogDAOThread = new Thread(configLogDAO);
				configLogDAOThread.run();
			}
		};
		
		logsModel.setPredicate(log -> log.getLogLevel().getLevel() >= configView.getLogLevel().getLevel()
		                              &&  configView.getLogTags().toLowerCase().contains(log.getTag().toLowerCase()));
		
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
