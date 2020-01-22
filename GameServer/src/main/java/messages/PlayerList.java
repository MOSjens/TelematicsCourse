package messages;

import java.util.LinkedHashMap;

public class PlayerList extends Message {
	
	private LinkedHashMap<Integer, PairReadyAlias> mapPlayerIdToAlias = new LinkedHashMap<Integer, PairReadyAlias>();

	public PlayerList() {
		super();
		this.setMessageType(MessageType.PLAYER_LIST);
	}

	public LinkedHashMap<Integer, PairReadyAlias> getMapPlayerIdToAlias() {
		return mapPlayerIdToAlias;
	}

	public void setMapPlayerIdToAlias(LinkedHashMap<Integer, PairReadyAlias> mapPlayerIdToAlias) {
		this.mapPlayerIdToAlias = mapPlayerIdToAlias;
	}
	
	

}
	