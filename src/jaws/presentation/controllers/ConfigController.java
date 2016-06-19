package jaws.presentation.controllers;

import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

import jal.business.log.Log;
import jaws.data.ConfigLogDAO;
import jaws.presentation.models.FilteredListModel;
import jaws.presentation.views.ConfigView;

import static trycrash.Try.tryCatch;

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

				
			}
			
			@Override
			public void newPresetClicked() {

				
			}
			
			@Override
			public void loadPresetClicked() {

				
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
		
		configView = new ConfigView(presetDelegate, configDelegate, menuDelegate,
		                            logsModel, httpPortModel, threadModel);
	}
}
