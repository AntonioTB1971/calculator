package inaer.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

import inaer.shared.CalculatorService;
import inaer.shared.CalculatorServiceAsync;

public class CalculatorController {
	public enum ECommands {
		none, clear, clearCurrent, value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, dot, 
		opSign, opAdd, opSubstract, opMultiply, opDivide, opEqual, opPercent, opToBinary  
	}

	private CalculatorModel model;
	
	private CalculatorServiceAsync calculatorService;
	
	public CalculatorController(CalculatorModel calculatorModel) {
		this.model = calculatorModel;
		calculatorService = GWT.create(CalculatorService.class);
	}

	protected String getStringValue(double fValue) {
		NumberFormat fmt = NumberFormat.getDecimalFormat();
		return fmt.format(fValue);
	}
	 
	public void processCommand(ECommands command) {
		switch (command) {
		case clear:
			model.setAccumulator(0);
			model.setCurrentCommand(ECommands.none);
			model.setTextValue("0");
			model.setPartialInvalid(true);
			break;
		case clearCurrent:
			model.setTextValue("0");
			model.setPartialInvalid(true);
			break;
		case none:
			break;
		case opAdd:
		case opSubstract:
		case opMultiply:
		case opDivide:
			operateValue(command);
			break;
		case opEqual:
			operateValue(command);
			model.setAccumulator(0);
			model.setCurrentCommand(ECommands.none);
			break;
		case opPercent:
			operatePercent();
			break;
		case opSign:
			changeSign();
			break;
		case opToBinary:
			convertToBinary();
			break;
		case dot:
		case value0:
		case value1:
		case value2:
		case value3:
		case value4:
		case value5:
		case value6:
		case value7:
		case value8:
		case value9:
			updateValue(command);
			break;
		default:
			break;
			
		}
	}

	private void changeSign() {
		String value = model.getTextValue();
		if (value.startsWith("-")) {
			value = value.substring(1);
		} else {
			value = "-" + value;
		}
		model.setTextValue(value);
	}

	private void convertToBinary() {
		long lValue;
		try {
			lValue = Long.parseLong(model.getTextValue());
		} catch (Exception e) {
			model.setTextValue("Invalid integer");
			return;
		}
		calculatorService.convertToBinary(lValue, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				model.setTextValue("Error on RPC");
				model.setAccumulator(0);
				model.setCurrentCommand(ECommands.none);
			}

			public void onSuccess(String result) {
				model.setTextValue(result);
				model.setAccumulator(0);
				model.setCurrentCommand(ECommands.none);
			}
		});

	}

	private void operatePercent() {
		double a = model.getAccumulator();
		double b;
		try {
			b = Double.parseDouble(model.getTextValue());
			a = (a * b) / 100.0;
			model.setPartialInvalid(false);
			model.setTextValue(getStringValue(a));
		} catch (Exception e) {
		}
	}

	private void operateValue(ECommands command) {
		double a = model.getAccumulator();
		double b;
		try {
			b = Double.parseDouble(model.getTextValue());
			switch (model.getCurrentCommand()) {
			case opAdd:
				a += b;
				break;
			case opSubstract:
				a -= b;
				break;
			case opMultiply:
				a *= b;
				break;
			case opDivide:
				a /= b;
				break;
			default:
				// If we are not going to do some calculus, we update the accumulator to the current value.
				a = b;
				break;
			}
			model.setAccumulator(a);
			model.setCurrentCommand(command);
			model.setPartialInvalid(true);
			model.setTextValue(getStringValue(a));
		} catch (Exception e) {
		}
	}

	private void updateValue(ECommands command) {
		String value = model.getTextValue();
		if (model.isPartialInvalid())
			value = "";
		switch (command) {
		case dot:
			if (value == "") 
				value = "0";
			// Ignore additional dots in a floating point number.
			if  (value.contains(".") == false)
				value += '.';
		  break;
		case value0:
			value += '0';
		  break;
		case value1:
			value += '1';
		  break;
		case value2:
			value += '2';
		  break;
		case value3:
			value += '3';
		  break;
		case value4:
			value += '4';
		  break;
		case value5:
			value += '5';
		  break;
		case value6:
			value += '6';
		  break;
		case value7:
			value += '7';
		  break;
		case value8:
			value += '8';
		  break;
		case value9:
			value += '9';
		  break;
		default:
			break;
			
		}
		// we update the model if we can convert the text to number. 
		try {
			Double.parseDouble(value);
			model.setTextValue(value);
			if ((value != "0") && (value != "-0")) 
				model.setPartialInvalid(false);
		} catch (Exception e) {
			// swallow any conversion error.
		}
	}
	public String getTextValue() {
		return model.getTextValue();
	}

	public CalculatorServiceAsync getService() {
		return this.calculatorService;
	}
}
