package spelstegen.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The asynchronous version of the server interface.
 * @author Henrik Segesten
 */
public interface SpelstegenServiceAsync {

	public void logIn(String email, String password, AsyncCallback<Player> callback);
	
	public void addPlayer(Player player, AsyncCallback<Boolean> callback);
	
	public void updatePlayer(Player player, AsyncCallback<Boolean> callback);
	
	public void getPlayers(AsyncCallback<List<Player>> callback);
	
	public void addMatch(Match match, League league, AsyncCallback<Void> callback);
	
	public void getMatches(League league, AsyncCallback<List<Match>> callback);
	
	public void getLeagues(Player player, AsyncCallback<List<League>> callback);
	
}