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
		
		
		int firstOffSet = buffer.getInt();
		ArrayList<Integer> offsets = new ArrayList<Integer> () ;
		offsets.add(firstOffSet);
		while (!(buffer.remaining() <=  messageBody.length - firstOffSet)) {
			int lastOffset = buffer.getInt();
			offsets.add(lastOffset);
		}
		offsets.add(messageBody.length);
		this.playerIDS= new int [offsets.size() - 1];
	    this.readyStateS= new  int [offsets.size() - 1];
	   this.playerAliaseS= new  String [offsets.size() - 1];
	   
		for (int playerNum = 0; playerNum < offsets.size() - 1; playerNum++) {
			byte[] res = new byte[1000000000];
			int playerID = buffer.getInt();
		     int readyState = buffer.get();
			int aliasOffset = offsets.get(playerNum) + 5;
			int aliasLength = offsets.get(playerNum + 1) - aliasOffset;
			String  playerAlias = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(messageBody, aliasOffset, aliasLength)).toString();
		    	buffer.get(res, 0, aliasLength);
			this.playerIDS[playerNum]= playerID ;
		    	 this.readyStateS[playerNum]=readyState;
		    	  this.playerAliaseS[playerNum]=playerAlias;
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
	
	  @Override
	public String toString() {
		  String reslut = new String();
		for(int i = 0; i < playerAliaseS.length; i++) {
			reslut += ("ID of player: " + playerIDS[i] + " status of player: " + readyStateS[i] + " alias of player: " + playerAliaseS[i] +"\n");
		}
		return reslut;
	}


}
