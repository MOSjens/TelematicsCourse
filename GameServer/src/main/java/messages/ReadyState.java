package messages;

/** enum for the ready state with 2 for disconnected
 * @author IG4
 *
 */
public enum ReadyState {

	NOT_READY(0), READY(1), DISCONNECTED(2);

	ReadyState(int i) {
		valueOfReadyState =i;
	}
	
	private int valueOfReadyState;
	
    public int getValue() {
        return valueOfReadyState;
    }
}
