package server;

import client.Client;
import messages.Message;

public class IncomingMessage {
    private Message message;
    private Client sourceClient;

    public IncomingMessage (Message message, Client sourceClient) {
        this.message = message;
        this.sourceClient = sourceClient;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Client getSourceClient() {
        return sourceClient;
    }

    public void setSourceClient(Client sourceClient) {
        this.sourceClient = sourceClient;
    }
}
