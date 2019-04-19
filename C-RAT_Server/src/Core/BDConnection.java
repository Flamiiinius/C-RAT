package Core;

/**
 * C-RAT Server
 * author @ThePoog
 * MasterProject @SINTEF
 * November 2018
 */

import Domain.*;
import Messages.NewRisk;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BDConnection {
    private static final String driver ="com.mysql.jdbc.Driver";
    private static final String username = "root";
    private static final String password = "1234";
    private static final String database = "masterthesis";
    private static final String url = "jdbc:mysql://localhost/" + database + "?useSSL=false";

    private  Connection connect = null;
    private  Statement statement = null;
    private  PreparedStatement preparedStatement = null;
    private  ResultSet resultSet = null;

    private  void establishConnection(){
        try {
            // This will load the MySQL driver
            Class.forName(driver);

            connect = DriverManager.getConnection(url,username,password);

        } catch (Exception e) {
            PrintLog("SQLException_Establishing_Connection");
        }
    }

    private  void close(){
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            PrintLog("SQLException_Close_Connection");
        }
    }

    /*Fetch 1 user to the database */
    public  User getUserByName(String username){

        String query = "SELECT * from users WHERE name='" + username + "'";

        try {
            establishConnection();
            preparedStatement = connect.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            // ResultSet is initially before the first data set
            while (resultSet.next()) { // only iterate once;
                int userId = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String children = resultSet.getString("Children");
                String position = resultSet.getString("Position");
                close();
                return new User(userId,name,position,null,null,children);
            }

        } catch (Exception e) {
            PrintLog("Error: " + query);
            return null;
        }finally {
        close();
        }

        //this return statement will never happen
        //either sends user or sends exception
        return null;
    }

    //called on Login
    public  void  getPasswordDetails(Domain.Authenticator myAuthenticator) throws Exception {
        String query = "SELECT * from userpassword WHERE userId='" +  myAuthenticator.getUserId() + "'";

        try {
            establishConnection();
            preparedStatement = connect.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            // ResultSet is initially before the first data set
            while (resultSet.next()) { // only iterate once;
                int userId = resultSet.getInt("UserId");
                byte[] salt = resultSet.getBytes("salt");
                byte[] hash = resultSet.getBytes("Hash");
                myAuthenticator.setSalt(salt);
                myAuthenticator.setHash(hash);
            }
        } catch (Exception e) {
            throw e;
        }finally {
            close();
        }
    }

    //Building the user tree
    public HashMap<Integer,User> getMultipleUsersById(User root){

        HashMap<Integer,User> userMap = new HashMap<>();
        userMap.put(root.getUserId(),root);
        List<Integer> childrenList = root.getChildren();

        if(childrenList==null){
            return userMap;
        }

        if(childrenList.isEmpty()){
            return userMap;
        }

        String query = "SELECT * from users WHERE ID in ('";
        for(int i=0; i< childrenList.size();i++){
            query = query.concat(childrenList.get(i) + "','");
        }

        query = query.substring(0, query.length() - 3); //removing last char from String (,)
        query = query.concat("');");

        try {
            establishConnection();
            preparedStatement = connect.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            // ResultSet is initially before the first data set
            while (resultSet.next()) {

                int userId = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String children = resultSet.getString("Children");
                String position = resultSet.getString("Position");

                User u = new User(userId,name,position,root.getUserId(),root.getUsername(),children);
                userMap.put(userId,u);
            }
        } catch (Exception e) {
            PrintLog("Error: " + query);
        }finally {
            close();
        }

        //If user as grandchildren
        HashMap<Integer,User> tMap = new HashMap<>();
        for(int i=0; i< childrenList.size();++i){
            User tUser = userMap.get(childrenList.get(i));
            if(!tUser.getChildren().isEmpty()){
             tMap = getMultipleUsersById(tUser);
             userMap.putAll(tMap);
            }
        }

        return userMap;
    }

    //creating an HashMap with users
    public HashMap<Integer,Risk> getRiskByUser(User root,HashMap<Integer,User> userMap) throws InterruptedException {

        String query="";

        if(root.getChildren().isEmpty()){ // is a riskOwner
             query = "SELECT * from risks WHERE riskOwner = " + root.getUserId();
        }else{
            String searchIntegers = getChildrenArray(root,userMap).substring(1); //removing the 1st character ","
            query = "SELECT * from risks WHERE manager in (" + searchIntegers + ")";
        }

        return getRisk(query,userMap);
    }

    private HashMap<Integer,Risk> getRisk( String query,HashMap<Integer,User> userMap) {

        HashMap<Integer,Risk> userRisks = new HashMap<>();

        try {
            establishConnection();
            preparedStatement = connect.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            // ResultSet is initially before the first data set
            while (resultSet.next()) { // only iterate once;
                int riskId = resultSet.getInt("riskId");
                int manager = resultSet.getInt("manager");
                int riskOwner = resultSet.getInt("riskOwner");
                String description = resultSet.getString("description");
                String asset = resultSet.getString("asset");
                Date creationDate = resultSet.getDate("creationDate");
                float nrOccurYear = resultSet.getFloat("nrOccurYear");
                float cost= resultSet.getFloat("cost");
                float costyear = resultSet.getFloat("costyear");
                Risk newRisk = new Risk(riskId,manager,riskOwner,description,asset,creationDate,nrOccurYear,cost,costyear);
                userRisks.put(riskId,newRisk);
            }

        } catch (Exception e) {
            PrintLog("Error: " + query);
        }finally {
            close();
        }

        return userRisks;
    }

    //creating newRisk
    public Risk setNewRisk(NewRisk myNewRisk) throws InterruptedException {

        String query = "INSERT INTO risks (manager, riskOwner, description, asset,creationDate) VALUES"
                + "(?,?,?,?,?)";


        Risk r = null;

        try {
            establishConnection();
            preparedStatement = connect.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, myNewRisk.getManagerId());
            preparedStatement.setInt(2, myNewRisk.getRiskOwnerId());
            preparedStatement.setString(3, myNewRisk.getDescripton());
            preparedStatement.setString(4, myNewRisk.getAsset());
            Date date = java.sql.Date.valueOf(java.time.LocalDate.now());
            preparedStatement.setDate(5, date);
            preparedStatement.executeUpdate();

            //get ID
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            r =  new Risk(id,myNewRisk.getManagerId(),myNewRisk.getRiskOwnerId(),myNewRisk.getDescripton(),
                    myNewRisk.getAsset(),date);

        }catch (Exception e){
            PrintLog("Error: " + query);
            return null;
        }finally {
            close();
        }

        return r;
    }

    public boolean setUpdatedRisk(Risk updatedRisk){

        String query = "UPDATE risks SET description = ?, asset = ?, nrOccurYear = ?, cost = ?, costyear = ?"
        + "WHERE riskId = ?";

        try {
            establishConnection();
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, updatedRisk.getDescription());
            preparedStatement.setString(2, updatedRisk.getAsset());
            preparedStatement.setDouble(3,updatedRisk.getNrOcuurYear());
            preparedStatement.setDouble(4,updatedRisk.getCost());
            preparedStatement.setDouble(5,updatedRisk.getCostYear());
            preparedStatement.setInt(6, updatedRisk.getRiskId());
            preparedStatement.executeUpdate();


        }catch (Exception e){
            PrintLog("Error: " + query);
            return false;
        }finally {
            close();
        }

        return true;
    }

    public boolean deleteRisk(Risk r) throws InterruptedException {
        String query = "DELETE FROM risks WHERE riskId= " + r.getRiskId();

        try {
            establishConnection();
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.executeUpdate();

        }catch (Exception e){
            PrintLog("ERROR: " + query);
            return false;
        }finally {
            close();
        }

        return true;
    }

    //support method
    private static String getChildrenArray(User u,HashMap<Integer,User> userMap){

        String userString = ",'" +  u.getUserId() + "'";

        List<Integer> childList = u.getChildren();

        for(int i=0; i< childList.size();++i){
           User temp =  userMap.get(childList.get(i));
           String childrenString = getChildrenArray(temp,userMap);
           userString = userString.concat(childrenString);
        }

        return userString;
    }


    //Used by the admin
    public boolean storePasswordForUser(int userId, byte[] salt, byte[] hash){

        try {
            establishConnection();

            String query = " INSERT INTO userpassword (userId, salt, hash)  values (?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.setInt(1, userId);
            preparedStmt.setBytes(2,salt);
            preparedStmt.setBytes(3,hash);
            preparedStmt.execute();

            close();
        } catch (SQLException e) {
           return false;
        }finally {
            close();
        }
        return true;
    }
    //Auxiliar method to print to LogFile
    private void PrintLog(String logmessage){
        java.util.Date date = new java.util.Date();
        Timestamp ts=new Timestamp(date.getTime());
        System.out.println( "[" + ts +"] " + logmessage);
        return;
    }
}
