package inaer.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

import inaer.shared.CalculatorData;

public class CalculatorDataView implements IsWidget, EntryPoint {
	protected static final int WIDTH = 320;
	protected static final int HEIGHT = 240;

	private class DataPagedResult extends ArrayList<CalculatorData> implements PagingLoadResult<CalculatorData> {
		private static final long serialVersionUID = 1L;
		private int offset = 0;

		@Override
		public List<CalculatorData> getData() {
			List<CalculatorData>  result = subList(getOffset(), size());
			return result;
		}

		@Override
		public int getOffset() {
			return offset;
		}

		@Override
		public void setOffset(int offset) {
			offset = 0;
		}

		@Override
		public int getTotalLength() {
			return size();
		}

		@Override
		public void setTotalLength(int totalLength) {
			// Not implemented
		}
	}

	private static final CalculatorDataProperties props = GWT.create(CalculatorDataProperties.class);

	private ContentPanel panel;
	private DataPagedResult dataList = new DataPagedResult();
	private CalculatorDataPageLoader dataLoader;

	public void clearData() {
		dataList.clear();
	}

	public void addData(CalculatorData dataObj) {
		dataList.add(dataObj);
	}

	@Override
	public Widget asWidget() {
		if (panel == null) {
			final DataProxy<PagingLoadConfig, PagingLoadResult<CalculatorData>> dataPagingLoader = new DataProxy<PagingLoadConfig, PagingLoadResult<CalculatorData>>() {
				@Override
				public void load(PagingLoadConfig loadConfig, Callback<PagingLoadResult<CalculatorData>, Throwable> callback) {
					int offset = loadConfig.getOffset();
					dataList.setOffset(offset);
					callback.onSuccess(dataList);
				}
			};

			ColumnConfig<CalculatorData, String> date = new ColumnConfig<CalculatorData, String>(props.timestamp(), 160, "Date");
			ColumnConfig<CalculatorData, Double> number = new ColumnConfig<CalculatorData, Double>(props.number(), 60, "Number");
			ColumnConfig<CalculatorData, String> binary = new ColumnConfig<CalculatorData, String>(props.binary(), 90, "Binary");

			List<ColumnConfig<CalculatorData, ?>> columns = new ArrayList<ColumnConfig<CalculatorData, ?>>();
			columns.add(date);
			columns.add(number);
			columns.add(binary);

			ColumnModel<CalculatorData> cm = new ColumnModel<CalculatorData>(columns);

			ListStore<CalculatorData> store = new ListStore<CalculatorData>(props.key());

			dataLoader = new CalculatorDataPageLoader(dataPagingLoader);
			dataLoader.setRemoteSort(true);
			dataLoader.setLimit(5);
			dataLoader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, CalculatorData, PagingLoadResult<CalculatorData>>(store));

			final Grid<CalculatorData> grid = new Grid<CalculatorData>(store, cm);

			grid.setLoadMask(true);
			grid.setLoader(dataLoader);
			grid.setAllowTextSelection(true);
			grid.setHideHeaders(false);
			grid.getView().setStripeRows(true);
			grid.getView().setColumnLines(true);
			grid.setBorders(false);
			grid.setWidth(WIDTH);

			VerticalLayoutContainer vl = new VerticalLayoutContainer();
			vl.add(grid, new VerticalLayoutData(1, 1));

			panel = new ContentPanel();
			panel.setVisible(false);
			panel.setHeading("Datastore contents");
			panel.add(vl);
			panel.setWidth("400px");
			panel.setHeight("240px");
		}
		return panel;
	}

	public void show() {
		dataLoader.load();
		panel.show();
	}

	public void hide() {
		panel.hide();
	}

	@Override
	public void onModuleLoad() {
	}
}
