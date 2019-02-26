package Core;

 /**
  * C-RAT Server
  * author @ThePoog
  * MasterProject @SINTEF @UiO
  * November 2018
  */

import java.net.*;
import java.io.*;

/**
 * Main Server class
 * Responsible for receiving new connections
 * and assign a new thread to each one
 */
public class Server {

    public static void main(String[] args) throws IOException{

        //Server Listening on port 5056
        ServerSocket ss = new ServerSocket(5056);

        //running infinite loop for getting clients request
        while(true){
            Socket s = null;
            try{
                //socket object to receive incoming clients requests
                s = ss.accept();
                System.out.println("A new client is connected: " + s);

                //obtaining input and output streams

                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(s.getInputStream());

                //create a new threadObject
                System.out.println("Staring new thread");
                Thread t = new ClientHandler(s,in,out);
                t.start();
            }catch(Exception e){
                System.out.println("Close connection " + s);
                s.close();
               // e.printStackTrace();
            }
        }
    }
}
