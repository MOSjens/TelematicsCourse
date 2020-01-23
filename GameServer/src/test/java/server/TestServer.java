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
    
    @Test
    public void testServer() {

    	 
    	ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
    	executor.submit(() -> {
    		startServer();
    	});
    	ScheduledFuture<?> future = executor.schedule(() -> {
    	    testConnectClient();
    	}, 500, TimeUnit.MILLISECONDS);
    	try {
			executor.awaitTermination(2500,  TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	future.cancel(true);
    	
    }
    

}
