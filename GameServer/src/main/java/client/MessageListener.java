package client;

import java.util.EventListener;

public interface MessageListener extends EventListener {
    void handleMessage( MessageEvent e );
}
