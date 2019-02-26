package Messages;

/**
 * C-RAT Server & Client
 * author @ThePoog
 * MasterProject @SINTEF & @UiO
 * November 2018
 */

import java.io.Serializable;

public class NewRisk implements Serializable {
    private static final long serialVersionUID = 123L;

    private final int managerId;
    private final int riskOwnerId;
    private final String descripton;
    private final String asset;

    public NewRisk(int managerId, int riskOwnerId, String descripton, String asset){
        this.managerId = managerId;
        this.riskOwnerId = riskOwnerId;
        this.descripton = descripton;
        this.asset = asset;
    }

    public int getManagerId(){
        return managerId;
    }

    public int getRiskOwnerId(){
        return riskOwnerId;
    }

    public String getDescripton(){
        return  descripton;
    }

    public String getAsset(){
        return asset;
    }

}
