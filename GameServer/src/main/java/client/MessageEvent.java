package client;

import messages.Message;

import java.util.EventObject;

public class MessageEvent extends EventObject {
    private Message message;

    public MessageEvent(Object source, Message message) {
        super(source);
        this.message = message;
    }

    public Message getMessage() {
        return this.message;
    }
}
