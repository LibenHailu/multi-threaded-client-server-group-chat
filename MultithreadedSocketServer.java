
package multithreadedsocketserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class MultithreadedSocketServer {

    private static ArrayList<ServerClientThread> clients = new ArrayList<>();
    
    public static void main(String[] args) throws Exception {
        
        try {
//            create socket server object
            ServerSocket server = new ServerSocket(1234);
            int clientCounter = 0;
            System.out.println("Server Started on port 1234");
            while (true) {
                //server accepts clients' connection request
                Socket serverClient = server.accept();  
                clientCounter++;
                System.out.println("Client " + clientCounter + " is online");
                
                //send  the request to a separate thread for concurency
                ServerClientThread sct = new ServerClientThread(serverClient, clientCounter,clients); 
                clients.add(sct);
                sct.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}


class ServerClientThread extends Thread {


    private Socket serverClient;
    private int clientNo;
    private ArrayList<ServerClientThread> clients;
    private BufferedReader inStream;
    private PrintWriter outStream;

    public ServerClientThread(Socket socket, int clientCounter, ArrayList<ServerClientThread> clients) throws IOException {
        this.serverClient = socket;
        this.clientNo = clientCounter;
        this.clients = clients;
        inStream = new BufferedReader(new InputStreamReader(serverClient.getInputStream()));
        outStream = new PrintWriter(serverClient.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            String clientMessage = "", serverMessage = "";
            while (!clientMessage.equals("exit")) {
                clientMessage = inStream.readLine();
                if (clientMessage.equals("bye")) {
                    outStream.print("bye Client " + clientNo);
                    
                }
                for (ServerClientThread client : clients) {
                    client.outStream.println("Client " + clientNo + ":" + clientMessage);

                }
            }
            inStream.close();
            outStream.close();
            serverClient.close();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            System.out.println("bye Client " + clientNo);
        }
    }
}
