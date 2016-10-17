package inaer.client;

import java.util.ArrayList;
import java.util.List;

import inaer.client.CalculatorController.ECommands;

interface CalculatorModelObserver {
	  public void onModelChange();
}


public class CalculatorModel {
	private String textValue;
	private double accumulator;
	private boolean partialInvalid;
	private CalculatorController.ECommands currentCommand;
	
	private List<CalculatorModelObserver> observers =  new ArrayList<CalculatorModelObserver>();
	
	protected void notifyObservers() {
	    for (CalculatorModelObserver o : observers)
	      o.onModelChange();
	}
    public void addObserver(CalculatorModelObserver anObserver) {
	   observers.add(anObserver);
	}

	
	public CalculatorModel() {
		textValue = "0";
		accumulator = 0;
		partialInvalid = true;
		setCurrentCommand(ECommands.none);
	}

	public double getAccumulator() {
		return accumulator;
	}

	public void setAccumulator(double accumulator) {
		this.accumulator = accumulator;
		notifyObservers();
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
		notifyObservers();
	}
	public CalculatorController.ECommands getCurrentCommand() {
		return currentCommand;
	}
	public void setCurrentCommand(CalculatorController.ECommands currentCommand) {
		this.currentCommand = currentCommand;
		notifyObservers();
	}
	public boolean isPartialInvalid() {
		return partialInvalid;
	}
	public void setPartialInvalid(boolean partialInvalid) {
		this.partialInvalid = partialInvalid;
		notifyObservers();
	}

	
}
