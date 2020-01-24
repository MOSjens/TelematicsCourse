package messages;

import java.util.LinkedHashMap;

public class PlayerListMessage extends Message {
	
	private LinkedHashMap<Integer, PairReadyAliasMessage> mapPlayerIdToAlias = new LinkedHashMap<Integer, PairReadyAliasMessage>();

	public PlayerListMessage() {
		super();
		this.setMessageType(MessageType.PLAYER_LIST);
	}

	public LinkedHashMap<Integer, PairReadyAliasMessage> getMapPlayerIdToAlias() {
		return mapPlayerIdToAlias;
	}

	public void setMapPlayerIdToAlias(LinkedHashMap<Integer, PairReadyAliasMessage> mapPlayerIdToAlias) {
		this.mapPlayerIdToAlias = mapPlayerIdToAlias;
	}
	
	

}
	