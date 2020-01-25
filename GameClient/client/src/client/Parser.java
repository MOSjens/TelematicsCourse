package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.Line;

import client.Client;

import java.net.*;

public class Parser {
	private  byte[] message;

	public Parser() {
		Socket server= new  Socket();
		
		InetSocketAddress remoteAddress=(InetSocketAddress)server.getRemoteSocketAddress();
		String host =remoteAddress.getHostName();
		int port =remoteAddress.getPort();
		
	
		
		try {
			byte[] message = new byte[0];
			ByteBuffer buffer = ByteBuffer.wrap(message);
			buffer.order(ByteOrder.BIG_ENDIAN);
			BufferedReader reader =new BufferedReader(new InputStreamReader(server.getInputStream()));
			//for(message=reader.readLine();Line !=null; Line =reader.read(ByteBuffer , off, len));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
