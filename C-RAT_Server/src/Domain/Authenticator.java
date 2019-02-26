package Domain;

/**
 * C-RAT Server
 * author @ThePoog
 * MasterProject @SINTEF
 * November 2018
 */

import Util.*;
import Core.*;

import java.util.Arrays;

/**
 * Class Is called upon login
 * Responsible for verifying the user/password combination
 */
public class Authenticator{ //NOT Serializable
    final private int userId;
    private byte[] salt;
    private byte[] hash;
    private static boolean b=true;

    public Authenticator(int userId){
        this.userId = userId;
    }

    public boolean verify(String password){
        try{
            BDConnection myConnection = new BDConnection();
            myConnection.getPasswordDetails(this);
        }catch (Exception e){
            System.out.println("error reading DB");
            return false;
        }

        if(salt!=null && hash!= null){

            byte[] hashedpassword = Security.createHash(password,salt);

            if(Arrays.equals(hash,hashedpassword)){
                return  true;
            }
        }
        return false;
    }

    public int getUserId(){
        return this.userId;
    }

    public void setSalt(byte[] salt){
        this.salt=salt;
    }

    public void setHash(byte[] hash){
        this.hash=hash;
    }

     public static boolean setPassword(int userId,String password){
        System.out.println("Setting Password: " + password + " for user: " + userId);
        byte[] salt = Security.createSalt();
        byte[] hash = Security.createHash(password,salt);
        BDConnection myConnection = new BDConnection();
        return myConnection.storePasswordForUser(userId, salt, hash);
    }

}

