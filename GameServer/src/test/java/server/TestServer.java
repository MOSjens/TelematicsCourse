package server;

import client.ExampleClient;
import org.junit.Test;

import java.io.IOException;

/**
 * To test the server you have to run both tests simultaneously.
 * First start the "startServer" which awaits a client to connect, then start the "testConnectClient" which
 * connects and works as a client.
 */

public class TestServer {

    @Test
    public void startServer() {
        Server.main(null);
    }

    @Test
    public void testConnectClient(){
        ExampleClient exampleClient = new ExampleClient();
        try {
            exampleClient.startConnection("127.0.0.1", 6666);

            // Example to Send
            byte[] data = new byte[] { (byte)0x1, (byte) 0x1, (byte)0x2,
                    0x0, (byte)0x0, 0x0, 0x46, 0x10, (byte)0xa2, (byte)0xd8, 0x08, 0x00, 0x2b,
                    0x30, 0x30, (byte)0x9d };

            exampleClient.sendMessage(data);
            Thread.sleep(2000);
            exampleClient.stopConnection();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
