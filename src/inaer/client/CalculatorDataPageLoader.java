package inaer.client;

import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;

import inaer.shared.CalculatorData;

public class CalculatorDataPageLoader extends PagingLoader<PagingLoadConfig, PagingLoadResult<CalculatorData>> {
  public CalculatorDataPageLoader(DataProxy<PagingLoadConfig, PagingLoadResult<CalculatorData>> proxy) {
    super(proxy);
  }

  @Override
  public void loadData(PagingLoadConfig config) {
    super.loadData(config);
  }
}
