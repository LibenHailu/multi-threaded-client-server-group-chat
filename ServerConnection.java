/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Liben
 */
public class ServerConnection implements Runnable {

    private Socket server;
    private BufferedReader inStream;

    public ServerConnection(Socket s) throws IOException {
        server = s;
        inStream = new BufferedReader(new InputStreamReader(server.getInputStream()));
    }

    @Override
    public void run() {
        while (true) {
            try {
                String serverResponse = inStream.readLine();
                System.out.println(serverResponse);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

}
