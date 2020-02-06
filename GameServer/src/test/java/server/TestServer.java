package server;

import client.ExampleClient;
import messages.Message;
import org.junit.Test;

import java.io.IOException;
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

    
    public void testConnectClient(byte[] data){
        ExampleClient exampleClient = new ExampleClient();
        try {
            exampleClient.startConnection("127.0.0.1", 6666);
            exampleClient.sendMessage(data);
            Thread.sleep(300);
            exampleClient.stopConnection();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }     
    }
    
    public void testConnectClientSend(byte[] data, byte[] data2, byte[] data3){
        ExampleClient exampleClient = new ExampleClient();
        try {
            exampleClient.startConnection("127.0.0.1", 6666);
            exampleClient.sendMessage(data);
            Thread.sleep(100);
            exampleClient.sendMessage(data2);
            Thread.sleep(3000);
            exampleClient.stopConnection();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }     
    }
    
    
    //@Test
    public void testServer() {
        // Example to Send
        byte[] dataMessage1 = new byte[] { 0x01,0x00,0x00,0x00,0x00,0x00,0x0b,(byte) 0xf0,(byte) 0x9f,(byte) 0xa6,
				(byte) 0x84,(byte) 0xf0,(byte) 0x9f,(byte) 0x90,(byte) 0xbf,(byte) 0xef,(byte) 0xb8,(byte) 0x8f };
        byte[] dataMessage2 = new byte[] { 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x06, 0x65, 0x6c, 0x6c, 0x65, 0x6c, 0x65 };
        byte[] dataMessage3 = new byte[] { 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x06, 0x65, 0x6c, 0x6c, 0x65, 0x6c, 0x65 };
    	 
    	ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
    	executor.submit(() -> {
    		startServer();
    	});
    	ScheduledFuture<?> future = executor.schedule(() -> {
    	    testConnectClient(dataMessage1);
    	}, 300, TimeUnit.MILLISECONDS);
    	ScheduledFuture<?> future2 = executor.schedule(() -> {
    	    testConnectClient(dataMessage2);
    	}, 300, TimeUnit.MILLISECONDS);
    	ScheduledFuture<?> future3 = executor.schedule(() -> {
    	    testConnectClient(dataMessage3);
    	}, 300, TimeUnit.MILLISECONDS);
    	try {
			executor.awaitTermination(1700,  TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	future.cancel(true);
    	future2.cancel(true);
    	future3.cancel(true);
    	
    }
    
    //@Test
    public void testServerPhase() {
        // Example to Send
    	
        byte[] dataMessage1 = new byte[] { 0x01,0x00,0x00,0x00,0x00,0x00,0x0b,(byte) 0xf0,(byte) 0x9f,(byte) 0xa6,
				(byte) 0x84,(byte) 0xf0,(byte) 0x9f,(byte) 0x90,(byte) 0xbf,(byte) 0xef,(byte) 0xb8,(byte) 0x8f };
        byte[] dataMessage2 = new byte[] { 0x01, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00 };
        byte[] dataMessage3 = new byte[] { 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x06, 0x65, 0x6c, 0x6c, 0x65, 0x6c, 0x65 };
    	 
    	ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
    	executor.submit(() -> {
    		startServer();
    	});
    	ScheduledFuture<?> future = executor.schedule(() -> {
    	    testConnectClientSend(dataMessage1, dataMessage2, dataMessage3);
    	}, 300, TimeUnit.MILLISECONDS);

    	try {
			executor.awaitTermination(8000,  TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	future.cancel(true);

    	
    }

}
