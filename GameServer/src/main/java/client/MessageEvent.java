package client;

import java.util.EventObject;

import messages.Message;

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
