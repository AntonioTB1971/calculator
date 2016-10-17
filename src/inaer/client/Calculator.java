package inaer.client;

import java.util.List;

import inaer.shared.CalculatorData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Calculator implements EntryPoint {
	/** 
	 * Those are the global variables.
	 */
	CalculatorView calcView;
	CalculatorController calcCtrl;
	CalculatorModel calcModel;
	CalculatorDataView calcDataView;

	TextButton showDataButton;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Create the calculator objects
		calcModel = new CalculatorModel();
		calcCtrl = new CalculatorController(calcModel);
		calcView = new CalculatorView(calcCtrl, RootPanel.get("calcContainer"));

		calcModel.addObserver(calcView);

		calcDataView = new CalculatorDataView();

		// Button to show/hide data dump.
		showDataButton = new TextButton();
		RootPanel.get("showDataContainer").add(showDataButton);
		    
	    showDataButton.setText("Show Datastore");
	    showDataButton.addSelectHandler(new SelectHandler() {
	    	public void onSelect(SelectEvent event) {
	    		if(calcDataView.asWidget().isVisible() == false) {
	    			calcCtrl.getService().getStoredData(new AsyncCallback<List<CalculatorData>>() {
	    				public void onFailure(Throwable caught) {
	    				}

	    				public void onSuccess(List<CalculatorData> result) {
	    					calcDataView.clearData();
	    					for (CalculatorData dataObj : result) {
	    						calcDataView.addData(dataObj);
	    					}
	    					calcDataView.show();
	    				}
	    			});
	    			showDataButton.setText("Hide DataStore");
	    		}
	    		else {
	    			calcDataView.hide();
	    		    showDataButton.setText("Show Datastore");
	    		}
	    	}
	    });    
	    RootPanel.get("showDataContainer").add(calcDataView.asWidget());
	}
}

