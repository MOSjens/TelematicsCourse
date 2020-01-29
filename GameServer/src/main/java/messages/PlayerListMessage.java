package messages;

import java.util.LinkedHashMap;

/** player list message with a list of all player to inform all players about ready state and alias of other players
 * @author IG4
 *
 */
public class PlayerListMessage extends Message {
	
	private LinkedHashMap<Integer, PairReadyAliasMessage> mapPlayerIdToAlias = new LinkedHashMap<Integer, PairReadyAliasMessage>();

	public PlayerListMessage() {
		super();
		this.setMessageType(MessageType.PLAYER_LIST);
	}
	
	public PlayerListMessage(LinkedHashMap<Integer, PairReadyAliasMessage> mapPlayerIdToAlias) {
		super();
		this.setMessageType(MessageType.PLAYER_LIST);
		this.setMapPlayerIdToAlias(mapPlayerIdToAlias);
	}

	public LinkedHashMap<Integer, PairReadyAliasMessage> getMapPlayerIdToAlias() {
		return mapPlayerIdToAlias;
	}

	public void setMapPlayerIdToAlias(LinkedHashMap<Integer, PairReadyAliasMessage> mapPlayerIdToAlias) {
		this.mapPlayerIdToAlias = mapPlayerIdToAlias;
	}
	
	

}
	