package messages;

public class PairReadyAlias { 
	  public final ReadyState readyState; 
	  public final String alias; 
	  public PairReadyAlias(ReadyState x, String y) { 
	    this.readyState = x; 
	    this.alias = y; 
	  } 

}
