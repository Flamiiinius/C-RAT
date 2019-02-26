package Messages;

import java.io.Serializable;

/**
 * C-RAT Server & Client
 * author @ThePoog
 * MasterProject @SINTEF @UiO
 * November 2018
 */

/**
 * Class Responsible for transport:
 * int LoginInformation
 */

public class LoginMessage  implements Serializable {
    private static final long serialVersionUID = 33L;
    private final String userName;
    private final String password;
    private byte[] sessionHash;

    public LoginMessage(String userName , String password){
        this.userName = userName;
        this.password = password;
    }

    public String getUserName(){
        return  this.userName;
    }

    public String getPassword(){
        return  this.password;
    }

    public void setSessionHash(byte[] sh){
        this.sessionHash = sh;
    }

    public byte[] getSessionHash(){
        return  sessionHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoginMessage lmsg = (LoginMessage) o;
        if (password != lmsg.password) {
            return false;
        }
        if (userName != lmsg.userName) {
            return false;
        }

        return true;
    }
}
