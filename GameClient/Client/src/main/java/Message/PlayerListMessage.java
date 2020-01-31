package Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import MessageType.GeneralMessageType;


public class PlayerListMessage extends Message {
	 private int [] playerIDS;
	 private int [] readyStateS;
	 private  String[] playerAliaseS;
	 
	 
	public PlayerListMessage(byte[] messageBody) {
		
		super ( GeneralMessageType.PLAYER_LIST,messageBody);
		ByteBuffer buffer = ByteBuffer.wrap(messageBody);
		buffer.order(ByteOrder.BIG_ENDIAN);
		int lastOffset = buffer.getInt();
		ArrayList<Integer> offsets = new ArrayList<Integer> () ;
		offsets.add(lastOffset);
		int newOffset;
		while ((newOffset = buffer.getInt()) <= lastOffset + 5) {
		lastOffset = newOffset;
		offsets.add(lastOffset);
		offsets.add(getPayloadLength());
		}
		for (int playerNum = 0; playerNum < offsets.size() - 1; playerNum++) {
			int playerID = buffer.getInt(offsets.get(playerNum));
		     int readyState = buffer.get(offsets.get(playerNum) + 4);
			int aliasOffset = offsets.get(playerNum + 5);
			int aliasLength = offsets.get(playerNum + 1) - aliasOffset;
			String  playerAlias = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(messageBody, aliasOffset, aliasLength)).toString();
		   
			
			this.playerIDS= new int [playerNum];
		    this.readyStateS= new  int [playerNum];
		   this.playerAliaseS= new  String [playerNum];
		    
			for (int i = 0; i < playerNum ; i++) {
		    	  this.playerIDS[i]= playerID ;
		    	 this.readyStateS[i]=readyState;
		    	  this.playerAliaseS[i]=playerAlias;
		    	  
		    	 
			}
			}
		
	}
	 public int getPlayerId(int i)
	 { 
		return this.playerIDS[i];
		 
	 }
	  public int getRoundState(int i)
	  {
		  return this.readyStateS[i];
		  
	  }
	  public String getPlayerAlias(int i)
	  {
		  
			  
		  return this.playerAliaseS[i];
	  }
	
	


}
