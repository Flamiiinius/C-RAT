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
 * int messageCode
 * boolean safeProtcol
 */


public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int messageCode;
    private final boolean safeProtocol;
    private byte[] sessionHash;

    public Message(int messageCode,boolean safeProtocol){
        this.messageCode = messageCode;
        this.safeProtocol = safeProtocol;
    }

    public int getCode(){
        return  this.messageCode;
    }

    public boolean getSafe(){
        return  this.safeProtocol;
    }

    public void setSessionHash(byte[] sessionHash){
        this.sessionHash = sessionHash;
    }

    public byte[] getSessionHash(){
        return this.sessionHash;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Message message = (Message) o;
        if (messageCode != message.messageCode) {
            return false;
        }
        if (safeProtocol != message.safeProtocol) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        if(safeProtocol)
            return messageCode;
        return messageCode*(-1);
    }
}

