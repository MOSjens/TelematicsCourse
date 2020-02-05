package messages;

/** timeout message signaling a timeout hit, is only used inside the server
 * @author IG4
 *
 */
public class TimeoutMessage extends Message {

    public TimeoutMessage() {
        super();
        this.setMessageType(MessageType.TIMEOUT);
    }
}
