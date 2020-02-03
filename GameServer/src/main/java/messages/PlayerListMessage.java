package messages;

import java.util.LinkedHashMap;

/** player list message with a list of all player to inform all players about ready state and alias of other players
 * @author IG4
 *
 */
public class PlayerListMessage extends Message {
	
	private LinkedHashMap<Integer, PairReadyAlias> mapPlayerIdToAlias = new LinkedHashMap<Integer, PairReadyAlias>();

	public PlayerListMessage() {
		super();
		this.setMessageType(MessageType.PLAYER_LIST);
	}
	
	public PlayerListMessage(LinkedHashMap<Integer, PairReadyAlias> mapPlayerIdToAlias) {
		super();
		this.setMessageType(MessageType.PLAYER_LIST);
		this.setMapPlayerIdToAlias(mapPlayerIdToAlias);
	}

	public LinkedHashMap<Integer, PairReadyAlias> getMapPlayerIdToAlias() {
		return mapPlayerIdToAlias;
	}

	public void setMapPlayerIdToAlias(LinkedHashMap<Integer, PairReadyAlias> mapPlayerIdToAlias) {
		this.mapPlayerIdToAlias = mapPlayerIdToAlias;
	}
	
	

}
	