package client;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * To test the server you have to run both tests simultaneously.
 * First start the "testStartClient" which awaits a client to connect, then start the "testConnectionClient" which
 * connects and works as a client.
 */
public class TestClient {

    @Test
    public void testStartClient(){

        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            Client testClient = new Client(serverSocket.accept());
            testClient.start();

            assertTrue(testClient.isAlive());
            assertEquals("Client", testClient.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testConnectClient(){
        ExampleClient exampleClient = new ExampleClient();
        try {
            exampleClient.startConnection("127.0.0.1", 6666);
            Thread.sleep(2000);
            exampleClient.stopConnection();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
