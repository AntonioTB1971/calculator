package inaer.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>CalculatorService</code>.
 */
public interface CalculatorServiceAsync {
	void convertToBinary(long lValue, AsyncCallback<String> asyncCallback) throws IllegalArgumentException;
	void getStoredData(AsyncCallback<List<CalculatorData>> asyncCallback);

}
