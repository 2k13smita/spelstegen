package spelstegen.client.widgets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spelstegen.client.LeagueChanger;
import spelstegen.client.MainApplication;
import spelstegen.client.SpelstegenServiceAsync;
import spelstegen.client.entities.League;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is the start page of the spelstegen client.
 * 
 * @author Henrik Segesten
 */
public class OverviewPanel extends Composite implements HistoryListener {
	
	private VerticalPanel leaguesPanel;
	private Map<Integer, League> allLeagues;
	private LeagueChanger leagueChanger;
	
	public OverviewPanel(SpelstegenServiceAsync spelstegenServiceAsync, LeagueChanger leagueChanger) {
		this.leagueChanger = leagueChanger;
		
		leaguesPanel = new VerticalPanel();
		leaguesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		ScrollPanel scrollPanel = new ScrollPanel(leaguesPanel);
		scrollPanel.setSize("780px", "500px");
		allLeagues = new HashMap<Integer, League>();
		//mainPanel.add(leaguesPanel);
		History.addHistoryListener(this);
		initWidget(scrollPanel);
		AsyncCallback<List<League>> callback = new AsyncCallback<List<League>>() {

			public void onFailure(Throwable arg0) {
				Window.alert("Misslyckades att hämta ligor. " + arg0.getMessage());
			}

			public void onSuccess(List<League> result) {
				for (League league : result) {
					addLeagueRow(league);
					allLeagues.put(league.getId(), league);
				}
			}
			
		};
		spelstegenServiceAsync.getLeagues(null, callback);
	}
	
	private void addLeagueRow(League league) {
		Hyperlink leagueLink = new Hyperlink(league.getName(), league.getId()+"");
		leagueLink.setStylePrimaryName("toplabel");
		Label nrMembers = new Label(league.getPlayers().size() + " spelare");
		HorizontalPanel firstRow = MainApplication.createStandardHorizontalPanel();
		firstRow.add(leagueLink);
		firstRow.add(nrMembers);
		leaguesPanel.add(firstRow);
	}

	public void onHistoryChanged(String arg) {
		int leagueId = Integer.parseInt(arg.trim());
		leagueChanger.changeToLeague(allLeagues.get(leagueId));
	}
	
}
