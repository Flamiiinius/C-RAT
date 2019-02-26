package Core;

/**
 * C-RAT Client
 * author @ThePoog
 * MasterProject @SINTEF & @UiO
 * November 2018
 */

import Domain.*;
import Messages.*;
import Messages.NewRisk;
import Pages.*;
import Util.*;

import javax.crypto.SealedObject;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.HashSet;

/*
* https://stackoverflow.com/questions/13772827/difference-between-static-and-final
 *Singleton class for communication with the server
 */
public class Client {
    private static boolean valid = true;
    private static Client instance;
    private final static int port = 5056; //Server Port
    private final static String host = "localhost"; //Server IP

    private static Socket socket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    // Change when setting the software if needed
    private static final int RSA_SIZE = 2048;
    private static boolean safeProtocol=true;
    private Encryptor myEncryptor; //needed is safeProtocol = true

    private static HashMap<Integer,Risk> myRisks = new HashMap<>();
    private static HashMap<Integer,User> users;
    private static byte[] sessionHash;

    /**
     * Establishing a connection between the client and the server
     */
    private Client(){
        try{
            //establishing connection with the server
            socket = new Socket(host,port);

            //Obtaining in and out streams
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Connection Established");

            if(safeProtocol && valid){
                try{
                    byte[] AESKey = startSafeProtocol();
                    System.out.println("Safe Protocol Completed");
                    myEncryptor = new Encryptor(AESKey);
                }catch (Exception e){
                    boolean a = ConfirmBox.display("Error","Error while establishing SafeProtocol," +
                            " Do you want to run in unsafe mode");
                    if(a){
                        safeProtocol=false;
                    }else{
                        valid   = false;
                    }
                }
            }

        }catch(IOException e){

            ConfirmBox.display("Error","Server unavailable");
            valid = false;
        }
    }

    /**
     * The Static initializer constructs the instance at class loading time;
     * this is to simulate a more involved construction process
     */
    static {
        instance = new Client();
    }

    public static boolean getValid(){
        return valid;
    }

    /*Static instance*/
    public static Client getInstance(){
        return instance;
    }

    private byte[] startSafeProtocol() throws Exception {

        System.out.println("Sending Message: 10");
        //sending SP with ClintPublicKey
        System.out.println("Starting Safe Protocol");
        Message m = new Message(10,true);

        SafeProtocol sp = new SafeProtocol();
        PubKey pk = new PubKey(RSA_SIZE);
        sp.setClientPublicKey(pk.getPublicKey());

        SafeProtocol n_sp = (SafeProtocol) sendComlpleteMessage(m,sp,false);

        System.out.println("Object received");

        byte[] AESkey = n_sp.getAESSecret(pk.getPrivateKey());
        return AESkey;
    }

    public boolean login(String userName,String password){
        System.out.println("Sending Message: 100");

        Message m = new Message(100,safeProtocol);
        LoginMessage lmsg = new LoginMessage(userName,password);

        try{

            LoginMessage new_LoginMessage =  (LoginMessage) sendComlpleteMessage(m,lmsg,safeProtocol);
            sessionHash = new_LoginMessage.getSessionHash();

            if(sessionHash==null){
                return false;
            }

            System.out.println("Login established");
            return true;

        }catch (Exception e){
            //e.printStackTrace();
            return  false;
        }
    }

    public HashMap<Integer,Risk> getRisks(){

        System.out.println("Sending Message: 101");
        Message message = new Message(101,safeProtocol,sessionHash);

        try{
            sendObject(message,safeProtocol);
            myRisks = (HashMap<Integer,Risk>) readInput();

            System.out.println("Risks: " + myRisks);
            return myRisks;
        }catch (Exception e){
            System.out.println("Error reading risks");
            return null;
        }
    }

    public HashMap<Integer, User> getUser(){

        System.out.println("Sending Message: 102");
        Message message = new Message(102,safeProtocol,sessionHash);
        try{
            sendObject(message,safeProtocol);

            users = (HashMap<Integer,User>)  readInput();
            System.out.println("Users :" + users);

            return users;

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error reading users");
            return null;
        }
    }

    public Risk newRisk(int riskOwner, String description, String asset) {

        System.out.println("Sending Message: 200");
        Message m = new Message(200,safeProtocol,sessionHash);
        NewRisk newRisk = new NewRisk(users.get(0).getUserId(),riskOwner,description,asset);

        try {
            return (Risk) sendComlpleteMessage(m,newRisk,safeProtocol);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public boolean updateRisk(Risk risk) {

        System.out.println("Sending Message: 210");
        Message m = new Message(210,safeProtocol,sessionHash);
        try {
            Message m2 = (Message) sendComlpleteMessage(m,risk,safeProtocol);
            if(m2.getCode()==1000){
                return true;
            }
        }catch (Exception e){
            //e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean deleteRisk(Risk r){

        System.out.println("Sending Message: 250");

        Message m = new Message(250,safeProtocol,sessionHash);
        try {
            Message m2 = (Message) sendComlpleteMessage(m,r,safeProtocol);
            if(m2.getCode()==1000) return true;
        }catch (Exception e){
            return false;
        }
        return false;
    }

    public void logout(){
        try {
            System.out.println("Closing this connection : " + socket);
            Message m = new Message(400,safeProtocol);

            sendObject(m,safeProtocol);

            socket.close();
            System.out.println("Connection closed");

            //closing resources
            in.close();
            out.close();
        } catch(Exception e){
            ConfirmBox cb = new ConfirmBox();
            cb.display("Error","Error closing connection with server");
        }
    }

    //Support methods
    private void sendObject(Serializable o,boolean safeProtocol) throws Exception {
        if (safeProtocol) {
            out.writeObject(myEncryptor.encryptObject(o));
        }else{
            out.writeObject(o);
        }

    }

    private Object sendComlpleteMessage(Serializable o1, Serializable o2,boolean safeProtocol) throws Exception {

        if (safeProtocol){
            out.writeObject(myEncryptor.encryptObject(o1));
            out.writeObject(myEncryptor.encryptObject(o2));
        }else{
            out.writeObject(o1);
            out.writeObject(o2);
        }

        Object o3=readInput();

        return o3;
    }

    private Object readInput() throws Exception {

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

}
