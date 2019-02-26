package Domain;

/**
 * C-RAT Server
 * author @ThePoog
 * MasterProject @SINTEF
 * November 2018
 */

import Messages.NewRisk;

import java.io.Serializable;
import java.util.Date;

public class Risk implements Serializable {

    private static final long serialVersionUID = 100L;

    private final int riskId;
    private final int manager;
    private final int riskOwner;
    private String description;
    private String asset;
    private final Date creationDate;
    private double nrOcuurYear;
    private double cost;
    private double costYear;


    //After the manager created and non-updated by the riskOwner
    public Risk(int riskId, int manager, int riskOwner, String description, String asset, Date creationTime) {
        this.riskId = riskId;
        this.manager = manager;
        this.riskOwner = riskOwner;
        this.description = description;
        this.asset = asset;
        this.creationDate = creationTime;
    }

    //When the risk contain all the information
    public Risk(int riskId, int manager, int riskOwner, String description,String asset, Date creationTime,
                float nrOccurences, float cost, float cYear) {
        this.riskId = riskId;
        this.manager = manager;
        this.riskOwner = riskOwner;
        this.description = description;
        this.asset = asset;
        this.creationDate = creationTime;
        this.nrOcuurYear = nrOccurences;
        this.cost = cost;
        this.costYear = cYear;
    }

    public void updateRisk(String description,String asset){
        this.description = description;
        this.asset = asset;
    }

    public void updateRisk(double nrOccur, double cost, double costYear){
        this.nrOcuurYear = nrOccur;
        this.cost = cost;
        this.costYear = costYear;
    }

    public int getRiskId(){
        return this.riskId;
    }

    public int getManager(){
        return this.manager;
    }

    public int getRiskOwner(){
        return this.riskOwner;
    }

    public String getDescription(){
        return this.description;
    }

    public String getAsset(){
        return this.asset;
    }

    public double getNrOcuurYear(){
        return  this.nrOcuurYear;
    }

    public double getCost(){
        return  this.cost;
    }

    public double getCostYear(){
        return this.costYear;
    }
}
