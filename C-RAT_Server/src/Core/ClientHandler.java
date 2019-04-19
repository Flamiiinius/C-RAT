package Core;

/**
 * C-RAT Server
 * author @ThePoog
 * MasterProject @SINTEF & @UiO
 * November 2018
 */
//C_RAT imports
import Domain.Authenticator;
import Domain.Risk;
import Domain.User;
import Messages.LoginMessage;
import Messages.Message;
import Messages.NewRisk;
import Messages.SafeProtocol;
import Util.Encryptor;
import Util.Security;
import sun.rmi.runtime.Log;

//Java imports
import javax.crypto.SealedObject;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static Util.Security.createHash;
import static Util.Security.createSalt;

/*
 * Responsible for handling communication with 1 client
 * https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
 */
public class ClientHandler extends Thread {
    private final Socket s;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private Encryptor myEncryptor;

    List<Risk> clientRisks = new ArrayList<Risk>();

    //Constructor
    public ClientHandler(Socket s,ObjectInputStream in, ObjectOutputStream out){
        this.s = s;
        this.in = in;
        this.out = out;
    }


    /**
     * Client Always start with a Msg Object identifying the MessageCode
     * IF the SafeProtocol was established:
     * -MyEncriptor is set for use
     * -Client will send SealedObject instead of Message
     *
     * Responsible to identify the request from client,
     * and send the appropriated answer
     *
     * With the help of util package decrypt and encrypt all the necessary objects
     */
    @Override
    public void run(){
        boolean running=true;
        Message msg; //Receive Users Requests

        //User Information
        User user = null;
        byte[] sessionHash=null;
        HashMap<Integer,User> userMap = null;
        HashMap<Integer,Risk> myRisks = null;

        //LogFiles Auxiliar variables
        long ThreadId = Thread.currentThread().getId();
        Date date= new Date();
        long timeSAux = date.getTime();


        PrintLog("New Thread " + ThreadId);
        do{
            try {
                msg = (Message) readInput(in);
                if(msg != null){
                    //
                    PrintLog(ThreadId + " New_Message: " + msg.getCode());

                    //Response msg to User
                    Message error_msg = new Message(404,msg.getSafe());
                    Message ack_msg = new Message(1000,msg.getSafe());

                    switch (msg.getCode()) {

                        case 10: //Safe Protocol

                            SafeProtocol sp = (SafeProtocol) readInput(in);

                            //Generating AESKey and sending it encrypted to Client
                            byte[] AESKey = Security.generateAESKey();
                            myEncryptor = new Encryptor(AESKey);
                            sp.setAESSecret(AESKey);
                            sendObject(false,sp,out);
                            break;

                        case 100: //Login

                            LoginMessage loginMessage= (LoginMessage) readInput(in);

                            //authenticate
                            String username = loginMessage.getUserName();
                            BDConnection myConnection = new BDConnection();
                            user = myConnection.getUserByName(username);

                            Authenticator myAuthenticator = new Authenticator(user.getUserId());
                            boolean valid = myAuthenticator.verify(loginMessage.getPassword());

                            if(valid){
                                sessionHash = createHash(username,createSalt());
                                loginMessage.setSessionHash(sessionHash);
                                PrintLog(ThreadId + " User_ID: " + user.getUserId() + " User_Name " + user.getUsername());
                                BDConnection dbc = new BDConnection();
                                userMap = dbc.getMultipleUsersById(user);
                                userMap.put(0,user); //root will go to index 0;
                            }

                            sendObject(msg.getSafe(),loginMessage,out);
                            break;

                        case 101: //getRisks

                            if(sessionHash!=null && msg.getSessionHash()!=null){
                                if(Arrays.equals(sessionHash,msg.getSessionHash())) {
                                    BDConnection dbc2 = new BDConnection();
                                    myRisks = dbc2.getRiskByUser(user,userMap);
                                    sendObject(msg.getSafe(),myRisks,out);
                                }
                                break;
                            }
                            break;


                        case 102: //getUsers
                            if(sessionHash!=null && msg.getSessionHash()!=null){
                                if (Arrays.equals(msg.getSessionHash(), sessionHash)) {
                                    BDConnection dbc3 = new BDConnection();
                                    dbc3.getMultipleUsersById(user);
                                    sendObject(msg.getSafe(),userMap,out);
                                    break;
                                }
                            }

                            sendObject(msg.getSafe(),error_msg,out);
                            break;

                        case 200: // newRisk

                            if(sessionHash!=null && msg.getSessionHash()!=null){
                                if (Arrays.equals(msg.getSessionHash(), sessionHash)) {
                                    NewRisk newRisk = (NewRisk) readInput(in);
                                    BDConnection dbc4 = new BDConnection();
                                    Risk r = dbc4.setNewRisk(newRisk);
                                    sendObject(msg.getSafe(),r,out);
                                    PrintLog(" User_ID: " + user.getUserId() + " NEW_RISK_ID " + r.getRiskId());
                                    break;
                                }
                            }

                            sendObject(msg.getSafe(),error_msg,out);
                            break;

                        case 210: //update risk

                            if(sessionHash!=null && msg.getSessionHash()!=null){
                                if (Arrays.equals(msg.getSessionHash(), sessionHash)) {
                                    Risk updatedRisk = (Risk) readInput(in);
                                    BDConnection dbc5 = new BDConnection();
                                    dbc5.setUpdatedRisk(updatedRisk);
                                    sendObject(msg.getSafe(),ack_msg,out);
                                    PrintLog(" User_ID: " + user.getUserId() + " Update_RISK_ID " + updatedRisk.getRiskId());
                                    break;
                                }
                            }

                            sendObject(msg.getSafe(),error_msg,out);
                            break;

                        case 250: //delete risk
                            if(sessionHash!=null && msg.getSessionHash()!=null){
                                if (Arrays.equals(msg.getSessionHash(), sessionHash)) {
                                    Risk deleteRisk = (Risk) readInput(in);
                                    BDConnection dbc6 = new BDConnection();
                                    dbc6.deleteRisk(deleteRisk);
                                    sendObject(msg.getSafe(),ack_msg,out);
                                    PrintLog(" User_ID: " + user.getUserId() + " Delete_RISK_ID " + deleteRisk.getRiskId());
                                    break;
                                }
                            }

                            sendObject(msg.getSafe(),error_msg,out);
                            break;


                         case 400: //LogOut
                             PrintLog(" User_ID: " + user.getUserId() + " LOGOUT " + this.s);
                             this.s.close();
                             running=false;
                            break;

                        default:
                            Message error = new Message(500,msg.getSafe());
                            sendObject(msg.getSafe(),error,out);
                            break;
                    }
                }
            } catch (Exception e) {
                if(running != false){
                    running=false;
                    PrintLog(" Foced_Close_Connection: " + s);
                }
                break;
            }

        }while(running);

        try {
            this.in.close();
            this.out.close();
            PrintLog("Close Thread " + ThreadId);
        }catch(IOException e){
            System.out.println("error closing socket resources!");
        }
    }


    //Below are Auxiliar methods, to help with tasks that affect most of the codes

    //Auxiliar output method
    private void sendObject(boolean safe, Serializable o,ObjectOutputStream out) throws Exception {
        if(safe){
            out.writeObject(myEncryptor.encryptObject(o));
        }else{
            out.writeObject(o);
        }

    }

    //Auxiliar input reading method
    private Object readInput(ObjectInputStream in) throws Exception {
        Object o=null;
        do{
            o = in.readObject();
        }while(o==null);
        if(o instanceof SealedObject){
            SealedObject so = (SealedObject) o;
            o = myEncryptor.decryptObject(so);
        }
        return o;
    }

    //Auxiliar method to print to LogFile
    private void PrintLog(String logmessage){
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        System.out.println( "[" + ts +"] " + logmessage);
        return;
    }
}
