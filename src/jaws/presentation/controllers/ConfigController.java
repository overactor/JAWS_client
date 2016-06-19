package jaws.presentation.controllers;

import javax.swing.SpinnerNumberModel;

import jal.business.log.Log;
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

				
			}
		};
		
		configView = new ConfigView(presetDelegate, configDelegate, menuDelegate,
		                            logsModel, httpPortModel, threadModel);
	}
}
