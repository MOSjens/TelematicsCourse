package messages;

public class PairReadyAliasMessage { 
	  public final ReadyState readyState; 
	  public final String alias; 
	  public PairReadyAliasMessage(ReadyState x, String y) { 
	    this.readyState = x; 
	    this.alias = y; 
	  } 

}
