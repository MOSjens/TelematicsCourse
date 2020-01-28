package server;

import client.ExampleClient;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * To test the server you have to run both tests simultaneously.
 * First start the "startServer" which awaits a client to connect, then start the "testConnectClient" which
 * connects and works as a client.
 */

public class TestServer {

    
    public void startServer() {
        Server.main(null);
    }

    
    public void testConnectClient(){
        ExampleClient exampleClient = new ExampleClient();
        try {
            exampleClient.startConnection("127.0.0.1", 6666);

            // Example to Send
            byte[] data = new byte[] { 0x01,0x00,0x00,0x00,0x00,0x00,0x0b,(byte) 0xf0,(byte) 0x9f,(byte) 0xa6,
    				(byte) 0x84,(byte) 0xf0,(byte) 0x9f,(byte) 0x90,(byte) 0xbf,(byte) 0xef,(byte) 0xb8,(byte) 0x8f };

            exampleClient.sendMessage(data);
            Thread.sleep(300);
            exampleClient.stopConnection();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testServer() {

    	 
    	ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
    	executor.submit(() -> {
    		startServer();
    	});
    	ScheduledFuture<?> future = executor.schedule(() -> {
    	    testConnectClient();
    	}, 300, TimeUnit.MILLISECONDS);
    	try {
			executor.awaitTermination(1700,  TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	future.cancel(true);
    	
    }
    

}
