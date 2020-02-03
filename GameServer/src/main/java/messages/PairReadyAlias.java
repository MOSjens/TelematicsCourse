package messages;

/** helper class to pair ready state and alias ( maybe unnecessary)
 * @author IG4
 *
 */
public class PairReadyAlias { 
	  public final ReadyState readyState; 
	  public final String alias; 
	  public PairReadyAlias(ReadyState x, String y) { 
	    this.readyState = x; 
	    this.alias = y; 
	  } 

}
