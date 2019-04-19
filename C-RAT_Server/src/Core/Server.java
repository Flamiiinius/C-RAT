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

        //Setting Log File
        String C_RAT_PATH="C:\\Users\\rodri\\C-RAT\\C-RAT_Server\\"; //change when installing
        String LogFilePath = C_RAT_PATH + "Logs.txt";

        PrintStream logs = new PrintStream(new File(LogFilePath));
        System.setOut(logs);

        //Server Listening on port 5056
        ServerSocket ss = new ServerSocket(5056);

        //running infinite loop for getting clients request
        while(true){
            Socket s = null;
            try{
                //socket object to receive incoming clients requests
                s = ss.accept();
                System.out.println("New_Connection " + s);

                //obtaining input and output streams
                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(s.getInputStream());

                //create a new threadObject
                Thread t = new ClientHandler(s,in,out);
                t.start();

            }catch(Exception e){
                System.out.println("Close_Connection " + s);
                s.close();
            }
        }
    }
}
