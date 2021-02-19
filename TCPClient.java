/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpclient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Liben
 */
public class TCPClient {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 1234);       
        ServerConnection serverConnection = new ServerConnection(socket);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter outStream = new PrintWriter(socket.getOutputStream(),true);
        
        Thread newCconn = new Thread(serverConnection); 
        newCconn.start();
            String clientMessage = "", serverMessage = "";
            while (true) {
                clientMessage = keyboard.readLine();
                String message = clientMessage.toLowerCase();
                if(message.equals("exit")){
                    socket.close();
                    outStream.close();
                    newCconn.stop();
                    System.exit(0);
                }
                outStream.println(clientMessage);
            }
    }

}
