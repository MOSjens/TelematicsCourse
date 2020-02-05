package client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

/**
 * This class is needed to provide a client which connects to the Server for the tests
 *
 * @author IG4
 */
public class ExampleClient {
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new DataOutputStream( clientSocket.getOutputStream() );
        in = new DataInputStream( clientSocket.getInputStream() );
    }

    public void sendMessage(byte[] data) throws IOException {
        out.write(data);
        //out.flush();
    }

    public byte[] awaitMassage() throws IOException {
        byte[] temp = new byte[1000];
        int number = 0;
        while (true){
            number = in.read( temp );
            System.out.println( number );
            if (number != -1) break;
        }
        byte[] resp = Arrays.copyOf(temp, number);
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
