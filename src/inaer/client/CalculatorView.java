package inaer.client;

import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import inaer.client.CalculatorController;
import inaer.client.CalculatorController.ECommands;

class CalculatorButton extends TextButton implements SelectHandler {
	private CalculatorController.ECommands buttonCommand = ECommands.none;
	private CalculatorController controller;
	
	public CalculatorButton(String text, CalculatorController controller, ECommands command) {
		super(text);
		this.controller = controller;
		this.buttonCommand = command;
		this.addSelectHandler(this);
	}
	
	@Override
	public void onSelect(SelectEvent event) {
		controller.processCommand(buttonCommand);
	}
}

public class CalculatorView  implements CalculatorModelObserver {
	
	private CalculatorController controller;
	private TextBox editBox;

	private void CreateCalculatorLayout(Panel mainPanel) {
		
		final int BUTTON_MARGIN = 4;
		final int BUTTON_WIDTH = 48 + BUTTON_MARGIN * 2;
		final int BUTTON_HEIGHT = 32 + BUTTON_MARGIN * 2;
		
		Margins margins = new Margins(BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN);
		
		
		DecoratorPanel panel = new DecoratorPanel();
		mainPanel.add(panel);
		
		VerticalLayoutContainer vLayout = new VerticalLayoutContainer();
		panel.add(vLayout);
		
		VerticalLayoutData vLData = new VerticalLayoutData(BUTTON_WIDTH * 5, BUTTON_HEIGHT);
		HorizontalLayoutData hLData = new HorizontalLayoutData(BUTTON_WIDTH, BUTTON_HEIGHT, margins);
		
		
		editBox = new TextBox();
		editBox.setAlignment(TextAlignment.RIGHT);
		
		HorizontalLayoutContainer hLayout;

		// First row
		hLayout = new HorizontalLayoutContainer();
		vLayout.add(hLayout, vLData);
		hLayout.add(editBox, new HorizontalLayoutData(BUTTON_WIDTH * 3, BUTTON_HEIGHT, margins));
		hLayout.add(new CalculatorButton("C", controller, ECommands.clear), hLData);
		hLayout.add(new CalculatorButton("CE", controller, ECommands.clearCurrent), hLData);

		// Second row
		hLayout = new HorizontalLayoutContainer();
		vLayout.add(hLayout, vLData);
		hLayout.add(new CalculatorButton("7", controller, ECommands.value7), hLData);
		hLayout.add(new CalculatorButton("8", controller, ECommands.value8), hLData);
		hLayout.add(new CalculatorButton("9", controller, ECommands.value9), hLData);
		hLayout.add(new CalculatorButton("+/-", controller, ECommands.opSign), hLData);
		hLayout.add(new CalculatorButton("%", controller, ECommands.opPercent), hLData);
		
		// Third row
		hLayout = new HorizontalLayoutContainer();
		vLayout.add(hLayout, vLData);
		hLayout.add(new CalculatorButton("4", controller, ECommands.value4), hLData);
		hLayout.add(new CalculatorButton("5", controller, ECommands.value5), hLData);
		hLayout.add(new CalculatorButton("6", controller, ECommands.value6), hLData);
		hLayout.add(new CalculatorButton("+", controller, ECommands.opAdd), hLData);
		hLayout.add(new CalculatorButton("-", controller, ECommands.opSubstract), hLData);
		
		// Fourth row
		hLayout = new HorizontalLayoutContainer();
		vLayout.add(hLayout, vLData);
		hLayout.add(new CalculatorButton("1", controller, ECommands.value1), hLData);
		hLayout.add(new CalculatorButton("2", controller, ECommands.value2), hLData);
		hLayout.add(new CalculatorButton("3", controller, ECommands.value3), hLData);
		hLayout.add(new CalculatorButton("*", controller, ECommands.opMultiply), hLData);
		hLayout.add(new CalculatorButton("/", controller, ECommands.opDivide), hLData);
		
		// Fifth row
		hLayout = new HorizontalLayoutContainer();
		vLayout.add(hLayout, vLData);
		hLayout.add(new CalculatorButton("0", controller, ECommands.value0), hLData);
		hLayout.add(new CalculatorButton(".", controller, ECommands.dot), hLData);
		hLayout.add(new CalculatorButton("BIN", controller, ECommands.opToBinary), hLData);
		hLayout.add(new CalculatorButton("=", controller, ECommands.opEqual), hLData);
		
		
	}
	
	public CalculatorView(CalculatorController controller, Panel placeHolder) {
		this.controller = controller;
		CreateCalculatorLayout(placeHolder);
		refreshView();
	}
	
	protected void refreshView() {
		String value = controller.getTextValue();
		if (value == "")
			editBox.setText("0");
		else
			editBox.setText(value);
	}
	
	@Override
	public void onModelChange() {
		refreshView();
	}

}
