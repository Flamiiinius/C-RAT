package Domain;

/**
 * C-RAT Server & Client
 * author @ThePoog
 * MasterProject @SINTEF & @UiO
 * November 2018
 */

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Risk implements Serializable {

    private static final long serialVersionUID = 100L;

    private final int riskId;
    private final int manager;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

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
    public Risk(int riskId, int manager, int riskOwner, String description, String asset, Date creationTime,
                double nrOccurences, double cost, double cYear) {
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

    //When the risk contain all the information - costYear
    public Risk(int riskId, int manager, int riskOwner, String description, String asset, Date creationTime,
                double nrOccurences, double cost) {
        this.riskId = riskId;
        this.manager = manager;
        this.riskOwner = riskOwner;
        this.description = description;
        this.asset = asset;
        this.creationDate = creationTime;
        this.nrOcuurYear = nrOccurences;
        this.cost = cost;
        this.costYear = cost / nrOccurences;
    }


    public int getRiskId() {
        return riskId;
    }

    public int getManager() {
        return manager;
    }

    public int getRiskOwner(){
        return this.riskOwner;
    }

    public Date getCreationDate() {
        return creationDate;
    }


    public String getDescription() {
        return description;
    }

    public String getAsset() {
        return asset;
    }

    public double getNrOcuurYear() {
        return nrOcuurYear;
    }

    public void setNrOcuurYear(double nrOcuurYear) {
        this.nrOcuurYear = nrOcuurYear;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCostYear() {
        return costYear;
    }

    public void setCostYear(double costYear) {
        this.costYear = costYear;
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

    public static HashMap<Integer,Risk> mockupRisks(){

        HashMap<Integer,Risk> risksMap= new HashMap<>();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();


        Risk r1 = new Risk(1000,2,1,"1stRisk",
                "MockupAsset", date,2.3,100,230);

        Risk r2 = new Risk(1001,2,3,"lalal",
                "MockupAsset", date,5,100,500);


        Risk r3 = new Risk(1002,2,4,"ups",
                "MockupAsset", date,3,400,1200);

        Risk r4 = new Risk(1003,2,1,"very cool",
                "MockupAsset", date,6.2,50,310);


        risksMap.put(1000, r1);
        risksMap.put(1001, r2);
        risksMap.put(1002, r3);
        risksMap.put(1003, r4);

        return  risksMap;
    }

}
