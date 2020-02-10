package messages;

/** timeout message signaling a timeout hit, is only used inside the server
 * @author IG4
 *
 */
public class TimeoutMessage extends Message {

    // Message that the timeout is replacing.
    private MessageType replacingMassage;

    public TimeoutMessage() {
        super();
        this.setMessageType(MessageType.TIMEOUT);
    }

    public TimeoutMessage (MessageType replacingMassage) {
        super();
        this.setMessageType(MessageType.TIMEOUT);
        this.replacingMassage = replacingMassage;
    }

    public MessageType getReplacingMassage() {
        return replacingMassage;
    }

}
