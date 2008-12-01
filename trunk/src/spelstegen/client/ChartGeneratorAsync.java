package spelstegen.client;

import spelstegen.client.entities.League;
import spelstegen.client.entities.Season;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChartGeneratorAsync {
    public void generateScoreHistoryChart(League league, Season season, AsyncCallback<String> callback);
} 