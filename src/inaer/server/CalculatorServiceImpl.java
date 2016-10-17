package inaer.server;

import inaer.shared.CalculatorData;
import inaer.shared.CalculatorService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
public class CalculatorServiceImpl extends RemoteServiceServlet implements CalculatorService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String convertToBinary(long number) throws IllegalArgumentException {
	    String result;
	    try {
	      result = Long.toBinaryString(number);
	      storeRequest(number, result);
	    } catch (Exception e) {
	      throw new IllegalArgumentException("Error converting to binary number");
	    }
	    return result;
	}

	private void storeRequest(long number, String result) {
		CalculatorStoredData data = new CalculatorStoredData();
		data.setTimestamp(new Date());
		data.setNumber(number);
		data.setBinary(result);

		EntityManager em = CalculatorEntityManager.get().createEntityManager();
		EntityTransaction transact = em.getTransaction();
		try {
			transact.begin();
			em.persist(data);
			transact.commit();
		} finally {
			if (transact.isActive())
				transact.rollback();
			em.close();
		}

	}

	@Override
	public List<CalculatorData> getStoredData() {
	    EntityManager em = CalculatorEntityManager.get().createEntityManager();
	    EntityTransaction transact = em.getTransaction();
	    List<CalculatorData> res = new ArrayList<CalculatorData>();
	    try {
	    	transact.begin();
	    	Query q = em.createQuery("SELECT t FROM CalculatorData t ORDER BY timestamp");
	    	@SuppressWarnings("unchecked")
	    	List<CalculatorStoredData> data = q.getResultList();
	    	transact.commit();
	    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	    	for (CalculatorStoredData dataObj : data) {
	    		CalculatorData temp = new CalculatorData();
	    		temp.timestamp = df.format(dataObj.getTimestamp());
	    		temp.number = dataObj.getNumber();
	    		temp.binary = dataObj.getBinary();
	    		res.add(temp);
	    	}
	    	em.close();
	    } catch (Exception e) {
	    	transact.rollback();
	    	em.close();
	    }
	    return res;
	}
}
