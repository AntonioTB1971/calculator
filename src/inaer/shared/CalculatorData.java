package inaer.shared;

public class CalculatorData implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  public String timestamp;
  public double number;
  public String binary;

  public String toString() {
    return "Date: " + timestamp + "; Number: " + number + "; Binary: " + binary;
  }
}
