package messages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardMessage extends Message {
	
	private int  roundLeft;
	private LinkedHashMap<Integer, Integer> mapPlayerIdToScore = new LinkedHashMap<Integer, Integer>();

	public ScoreboardMessage() {
		super();
		this.setMessageType(MessageType.SCOREBOARD);
	}
	
	public ScoreboardMessage(int roundLeft, LinkedHashMap<Integer, Integer> mapPlayerIdToScore) {
		super();
		this.setMessageType(MessageType.SCOREBOARD);
		this.setRoundLeft(roundLeft);
		this.setMapPlayerIdToScore(mapPlayerIdToScore);
	}
	
	public LinkedHashMap<Integer, Integer> getMapPlayerIdToScore() {
		return mapPlayerIdToScore;
	}

	public void setMapPlayerIdToScore(LinkedHashMap<Integer, Integer> mapPlayerIdToScore) {
		this.mapPlayerIdToScore = mapPlayerIdToScore;
	}

	public int getRoundLeft() {
		return roundLeft;
	}

	public void setRoundLeft(int roundLeft) {
		this.roundLeft = roundLeft;
	}
	public void sortMapByValue() {
	List<Map.Entry<Integer, Integer>> entries = new ArrayList<Map.Entry<Integer, Integer>>(mapPlayerIdToScore.entrySet());
	Collections.sort(entries, new Comparator<Map.Entry<Integer, Integer>>() {
		public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b){
			    return a.getValue().compareTo(b.getValue());
		}
	});
			
	LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
	for (Map.Entry<Integer, Integer> entry : entries) {
		sortedMap.put(entry.getKey(), entry.getValue());
	}
	mapPlayerIdToScore =  sortedMap;

	}
}
