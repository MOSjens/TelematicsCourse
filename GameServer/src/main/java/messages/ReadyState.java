package messages;

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
