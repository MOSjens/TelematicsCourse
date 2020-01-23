package client;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * To test the server you have to run both tests simultaneously.
 * First start the "testStartClient" which awaits a client to connect, then start the "testConnectionClient" which
 * connects and works as a client.
 */
public class TestClient {

    
    public void testStartClient(){

        try {
            ServerSocket serverSocket = new ServerSocket(6667);
            Client testClient = new Client(serverSocket.accept());
            testClient.start();

            assertTrue(testClient.isAlive());
            assertEquals("Client", testClient.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void testConnectClient(){
        ExampleClient exampleClient = new ExampleClient();
        try {
            exampleClient.startConnection("127.0.0.1", 6667);
            Thread.sleep(900);
            exampleClient.stopConnection();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testServer() {

    	 
    	ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
    	executor.submit(() -> {
    		testStartClient();
    	});
    	ScheduledFuture<?> future = executor.schedule(() -> {
    	    testConnectClient();
    	}, 400, TimeUnit.MILLISECONDS);
    	try {
			executor.awaitTermination(1500,  TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	future.cancel(true);
    	
    }

}
