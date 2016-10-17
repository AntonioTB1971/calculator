package inaer.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("calculator")
public interface CalculatorService extends RemoteService {
	String convertToBinary(long number) throws IllegalArgumentException;
	List<CalculatorData> getStoredData();
}
