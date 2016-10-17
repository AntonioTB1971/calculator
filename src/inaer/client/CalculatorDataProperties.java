package inaer.client;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import inaer.shared.CalculatorData;

public interface CalculatorDataProperties extends PropertyAccess<CalculatorData> {
  @Path("timestamp")
  ModelKeyProvider<CalculatorData> key();

  ValueProvider<CalculatorData, String> timestamp();

  ValueProvider<CalculatorData, Double> number();

  ValueProvider<CalculatorData, String> binary();

}

