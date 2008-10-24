package spelstegen.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import spelstegen.client.widgets.LoginPanel;
import spelstegen.client.widgets.RegisterResultPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MainApplication implements EntryPoint {

	public final static int NUMBER_OF_COLUMNS = 3;
	public final static int HORISONTAL_SPACING = 5;
	public final static int VERTICAL_SPACING = 15;
	private Grid mainTable;
	private List<Player> testPlayerData = new ArrayList<Player> (2);
	private ToggleButton tableButton = new ToggleButton("Tabell");
	private ToggleButton matchesButton = new ToggleButton("Matcher");
	
	private VerticalPanel contentPanel = new VerticalPanel();
	
	{
		Player p1 = new Player("1", "Åke");
		p1.changePoints(53);
		testPlayerData.add(p1);
		Player p2 = new Player("2", "Elsa");
		p2.changePoints(-34);
		testPlayerData.add(p2);
	}
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setWidth("100%");
		mainPanel.setSpacing(VERTICAL_SPACING);
		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Label topLabel = new Label("Epsilons squashstege", false);
		topLabel.setStylePrimaryName("toplabel");
		mainPanel.add(topLabel);
		
		// Top buttons
		tableButton.setDown(true);
		HorizontalPanel topButtonPanel = new HorizontalPanel();
		topButtonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		topButtonPanel.setSpacing(HORISONTAL_SPACING);
		topButtonPanel.add(tableButton);
		topButtonPanel.add(matchesButton);
		mainPanel.add(topButtonPanel);
		
		contentPanel.setSpacing(VERTICAL_SPACING);
		contentPanel.setStylePrimaryName("outer-border");
		contentPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		mainPanel.add(contentPanel);
		
		// Bottom buttons
		PushButton inputMatchButton = new PushButton("Registrera match");
		inputMatchButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				final RegisterResultPanel registerResultPanel = new RegisterResultPanel(testPlayerData);
				registerResultPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
					public void setPosition(int offsetWidth, int offsetHeight) {
			            int left = (Window.getClientWidth() - offsetWidth) / 3;
			            int top = (Window.getClientHeight() - offsetHeight) / 3;
			            registerResultPanel.setPopupPosition(left + 100, top);
			          }
				});
			}
		});
		PushButton loginButton = new PushButton("Logga in");
		loginButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				final LoginPanel loginPanel = new LoginPanel();
				loginPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
					public void setPosition(int offsetWidth, int offsetHeight) {
			            int left = (Window.getClientWidth() - offsetWidth) / 3;
			            int top = (Window.getClientHeight() - offsetHeight) / 3;
			            loginPanel.setPopupPosition(left + 100, top);
			          }
				});
			}
		});
		HorizontalPanel bottomButtonPanel = new HorizontalPanel();
		bottomButtonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		bottomButtonPanel.setSpacing(HORISONTAL_SPACING);
		bottomButtonPanel.add(inputMatchButton);
		bottomButtonPanel.add(loginButton);
		
		mainPanel.add(bottomButtonPanel);
		
		showTableView();
		RootPanel.get().add(mainPanel);
	}
	
	private void showTableView() {
		contentPanel.clear();
		
		mainTable = new Grid(3, NUMBER_OF_COLUMNS);
		mainTable.setCellSpacing(0);
		mainTable.setWidth("600px");
		mainTable.setText(0, 0, "#");
		mainTable.setText(0, 1, "Spelare");
		mainTable.setText(0, 2, "Poäng");
		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			mainTable.getCellFormatter().setStyleName(0, i, "table-caption"); 
		}
		
		populateTable(testPlayerData);
		contentPanel.add(mainTable);
	}
	
	private void showMatchView() {
		contentPanel.clear();
		
	}
	
	private void populateTable(List<Player> players) {
		int col = 0;
		Collections.sort(players);
		for (int i = 0; i < players.size(); i++) {
			mainTable.setText(i+1, col++, i+1 + "");
			mainTable.setText(i+1, col++, players.get(i).getPlayerName());
			mainTable.setText(i+1, col++, players.get(i).getPoints() + "");
			col = 0;
		}
		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			mainTable.getCellFormatter().setStyleName(players.size(), i, "table-caption");
		}
	}
	
	public static HorizontalPanel createHorizontalPanel() {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setSpacing(MainApplication.HORISONTAL_SPACING);
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		return panel;
	}

}
