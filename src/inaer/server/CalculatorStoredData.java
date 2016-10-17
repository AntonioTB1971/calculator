package inaer.server;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.appengine.api.datastore.Key;

@Entity
public class CalculatorStoredData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	private double number;
	private String binary;

	public Key getKey() {
		return key;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double input) {
		this.number = input;
	}

	public String getBinary() {
		return binary;
	}

	public void setBinary(String binary) {
		this.binary = binary;
	}

	public String toString() {
		return "Key: " + key + "; Date: " + timestamp.toString() + "; Number: " + number + "; Binary: " + binary;
	}

}
