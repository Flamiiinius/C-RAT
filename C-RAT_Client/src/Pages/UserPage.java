package Pages;

/**
 * C-RAT Client
 * author @ThePoog
 * MasterProject @SINTEF & @UiO
 * November 2018
 */

import Core.Client;
import Domain.Risk;
import Domain.User;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserPage extends Application {

    private Stage window;
    private HashMap<Integer,Risk> myRisks;
    private HashMap<Integer,User> users;
    private Client myClient = Client.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        myRisks = myClient.getRisks();
        users = myClient.getUser();

        while(myRisks==null || users==null){
            ConfirmBox.display("Connection Error","There was a problem with the connection, Do you want to ret again?");
            window.close();
        }

        window = primaryStage;
        window.setTitle("UserPage");

        ObservableList<Risk> tableObservalbeList = FXCollections.observableArrayList();
        myRisks.forEach((k, v) -> tableObservalbeList.add(v));


        //Layout Elements
        TableView<Risk> table = setUpTable();

        FilteredList<Risk> filteredData = new FilteredList<>(tableObservalbeList, p -> true);

        table.setItems(tableObservalbeList);

        TreeView<String> tree = setTreeView();

        //Layout Boxes
        HBox header = setHeader();
        header.getChildren().addAll(setRefreshButton(table,tree), createSpacer(),logoutButton());
        header.setAlignment(Pos.CENTER);
        HBox footer;
        VBox mainContainer = new VBox();
        HBox container2Level = new HBox();

        if (!users.get(0).getChildren().isEmpty()) {
            footer = setRiskManagerHBox(table,tree);
            footer.setAlignment(Pos.CENTER);
            container2Level.getChildren().addAll(tree, table);
            mainContainer.getChildren().addAll(header, container2Level, footer);
        } else {
            footer = setRiskOwnerHBox(table);
            footer.setAlignment(Pos.CENTER);
            mainContainer.getChildren().addAll(header,table,footer);
        }

        Scene scene = new Scene(mainContainer);
        window.setScene(scene);
        window.show();
    }

    private TreeView<String> setTreeView(){


        TreeItem<String> root = new TreeItem<>();
        root.setExpanded(true);

        TreeView<String> tree = new TreeView<>(root);
        tree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tree.setShowRoot(false);
        root.getChildren().add(newTreeItem(0));

        return tree;

    }

    private TreeItem<String> newTreeItem(Integer index){
        User tUser = users.get(index);
        TreeItem<String> item = new TreeItem<>(tUser.getName());
        item.setExpanded(true);
        tUser.getChildren().forEach(child -> {
            item.getChildren().add(newTreeItem(child));
        });

        return item;
    }

    private Button setRefreshButton(TableView<Risk> table,TreeView<String> tree){


        Button refreshButon = new Button("Refresh Table");
        refreshButon.setOnAction(e -> {
            myRisks = myClient.getRisks();

            table.getItems().removeAll(table.getItems());
            ObservableList<Risk> tableObservalbeList = treeTableSynchronization(tree);
            table.setItems(tableObservalbeList);
        });

        return  refreshButon;
    }

    private ObservableList<Risk> treeTableSynchronization(TreeView<String> tree){

        ObservableList<Risk> tableObservalbeList = FXCollections.observableArrayList();
        ObservableList<TreeItem<String>> treeSelected = tree.getSelectionModel().getSelectedItems();

        if(treeSelected.isEmpty()){
            myRisks.forEach((k, v) -> {  tableObservalbeList.add(v);});
        }else{
            myRisks.forEach((k, v) -> {

                String ownerName = users.get(v.getRiskOwner()).getName();
                String managerName = users.get(v.getManager()).getName();

                treeSelected.forEach(t -> {
                    if (t.getValue().equals(ownerName) || t.getValue().equals(managerName)) {
                        tableObservalbeList.add(v);
                    }
                });
            });
        }

        return tableObservalbeList;
    }

    private TableView<Risk> setUpTable(){

        //RiskID column
        TableColumn<Risk,Integer> riskIdColumn = new TableColumn<>("ID");
        riskIdColumn.setMinWidth(25);
        riskIdColumn.setCellValueFactory(new PropertyValueFactory<>("riskId"));

        //RiskManager column
        TableColumn<Risk,Integer> riskManagerColumn = new TableColumn<>("Manager");
        riskManagerColumn.setMinWidth(25);
        riskManagerColumn.setCellValueFactory(new PropertyValueFactory<>("manager"));


        //RiskID column
        TableColumn<Risk,Integer> riskOwnerColumn = new TableColumn<>("RiskOwner");
        riskOwnerColumn.setMinWidth(25);
        riskOwnerColumn.setCellValueFactory(new PropertyValueFactory<>("riskOwner"));


        //Description column
        TableColumn<Risk,String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setMinWidth(200);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));


        //Description column
        TableColumn<Risk,String> assetColumn = new TableColumn<>("Asset");
        assetColumn.setMinWidth(100);
        assetColumn.setCellValueFactory(new PropertyValueFactory<>("asset"));


        //Description column
        TableColumn<Risk,Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(50);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));


        //Description column
        TableColumn<Risk,Double> occurColumn = new TableColumn<>("Nr Occurences");
        occurColumn.setMinWidth(30);
        occurColumn.setCellValueFactory(new PropertyValueFactory<>("nrOcuurYear"));

        //Description column
        TableColumn<Risk,Double> costColumn = new TableColumn<>("Cost");
        costColumn.setMinWidth(30);
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        //Description column
        TableColumn<Risk,Double> costYearColumn = new TableColumn<>("Cost per Year");
        costYearColumn.setMinWidth(30);
        costYearColumn.setCellValueFactory(new PropertyValueFactory<>("costYear"));

        TableView table =  new TableView<>();
        table.getColumns().addAll(riskIdColumn,riskManagerColumn,riskOwnerColumn,descriptionColumn,assetColumn,
                dateColumn,occurColumn,costColumn,costYearColumn);
        return table;

    }

    private Node createSpacer() {
        final Region spacer = new Region();
        // Make it always grow or shrink according to the available space
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    private HBox setHeader() {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String stringDate = dateFormat.format(date);

        Text dateT = new Text(10, 60, stringDate);

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);

        Label nameLabel= new Label("Name: " + users.get(0).getName());

        Label positionLabel= new Label("Position: " +  users.get(0).getPosition());

        hbox.getChildren().addAll(nameLabel, createSpacer(),positionLabel, createSpacer(),dateT);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Date update = new Date();
                        String stringNewDate = dateFormat.format(update);
                        dateT.setText(stringNewDate);
                    }
                }
        ), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play(); // timeline.stop()

        return hbox;
    }

    private List<Integer> getLeaf(User u){
        List<Integer> list = new ArrayList<>();
        if( u.getChildren().isEmpty()){
            list.add(u.getUserId());
        }else{

            u.getChildren().forEach(e ->{
                User child = users.get(e);
                list.addAll(getLeaf(child));
            });
        }
        return list;
    }

    //Risk Manager Bottom Row
    private HBox setRiskManagerHBox(TableView<Risk> table,TreeView<String> tree){

        //DropDown for RiskManager
        ChoiceBox<String> choiceBox = new ChoiceBox<>();


        getLeaf(users.get(0)).forEach(user -> {
            String userName = users.get(user).getName();
            String value = (String) "" +  users.get(user).getUserId() + " " + userName;
            choiceBox.getItems().add(value);
        } );

        choiceBox.setValue(choiceBox.getItems().get(0));

        TextField descriptionInput = new TextField();
        descriptionInput.setPromptText("Description");
        descriptionInput.setMinWidth(200);

        TextField assetInput= new TextField();
        assetInput.setPromptText("Asset");
        assetInput.setMinWidth(100);

        Button addButon = new Button("Add");
        addButon.setOnAction(e -> managerAddButon(choiceBox,descriptionInput,assetInput,table));

        Button updateButton = new Button ("Update");
        updateButton.setOnAction(e -> managerUpdateButton(table, descriptionInput, assetInput));

        Button deleteButon = new Button("Delete");
        deleteButon.setOnAction(e -> managerDeleteButton(table));

        Button aggregateScenarios = new Button("Aggregate By Scenario");
        aggregateScenarios.setOnAction(e -> scenarioView(table,tree));

        Button aggregateAssets = new Button("Aggregate By Asset");
        aggregateAssets.setOnAction(e -> assetView(table,tree));

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);

        hBox.getChildren().addAll(choiceBox,descriptionInput,assetInput,addButon,updateButton,deleteButon,aggregateScenarios,aggregateAssets );

        return hBox;
    }

    private void managerAddButon(ChoiceBox choiceBox,TextField descriptionInput,TextField assetInput,
                                 TableView<Risk> table){

        String choice = (String) choiceBox.getValue();

        int riskOwner = Integer.parseInt(choice.substring(0,choice.indexOf(" ")));
        String description = descriptionInput.getText();
        String asset = assetInput.getText();

        System.out.println(description);
        System.out.println(asset);

        if( description.equals("") || asset.equals("")){
            ConfirmBox.alertBox("Error","There was a problem adding the risk, please try again.");
        }else{
           Risk r =  myClient.newRisk(riskOwner,description,asset);
           if (r != null){
                myRisks.put(r.getRiskId(),r);
                table.getItems().add(r);
            }else{
               ConfirmBox.alertBox("Error","There was a problem adding the new risk, please try again.");
            }
        }


    }

    private void managerUpdateButton(TableView<Risk> table,TextField descriptionInput,TextField assetInput) {

        ObservableList<Risk> selectedRisk = table.getSelectionModel().getSelectedItems();

        String description = descriptionInput.getText();
        String asset = assetInput.getText();

        if( description.equals("") && asset.equals("") ){
            ConfirmBox.alertBox("Error","Invalid input, please try again.");
            return;
        }

        Boolean answer = ConfirmBox.display("Update Risk", "Do you want to update the selected risk(s)");

        if(answer){

            selectedRisk.forEach(r -> {
                Risk r2= r;

                if(!description.equals("") )r2.setDescription(description);
                if(!asset.equals("") )r2.setAsset(asset);

                if(myClient.updateRisk(r)){ //if was accepted on server
                    Integer i = r.getRiskId();

                    if(!description.equals("")) myRisks.get(i).setDescription(description);
                    if(!asset.equals("")) myRisks.get(i).setAsset(asset);

                    if(!description.equals("")) r.setDescription(description);
                    if(!asset.equals(""))       r.setAsset(asset);

                    table.refresh();

                }else{
                    ConfirmBox.alertBox("Error","There was a problem updating the risk, please try again.");
                }

            });

        }
    }

    private void managerDeleteButton(TableView<Risk> table){
        ObservableList<Risk> selectedRisk;

        Boolean answer = ConfirmBox.display("Remove Risk", "Do you want to eliminate the selected risk(s)");

        if(answer) {
            selectedRisk = table.getSelectionModel().getSelectedItems();

            selectedRisk.forEach(r -> {
                if (myClient.deleteRisk(r)) { //if accepted on server
                    myRisks.remove(r.getRiskId());
                    table.getItems().remove(r);

                    table.refresh();
                } else {
                    ConfirmBox.alertBox("Error","There was a problem removing the risk, please try again.");
                }


            });
        }
    }

    private Risk aggregation(List<Integer> riskList,String scenarioDescripion, String assetName){

        double cost = 0;
        double freq = 0;

        for(int i=0;i < riskList.size();++i){
            Risk selected = myRisks.get(riskList.get(i));

            double c = selected.getCost() * selected.getNrOcuurYear();
            double f = selected.getNrOcuurYear();

            if(c > 0 && f > 0){
                cost +=c;
                freq += f;
            }
        }

        Risk selected = myRisks.get(riskList.get(0));
        return new Risk(0,selected.getManager(),selected.getManager(),scenarioDescripion,assetName,
                new Date(),freq,cost/freq);
    }

    private void scenarioView(TableView<Risk> table,TreeView<String> tree){

        table.getItems().removeAll(table.getItems());
        ObservableList<Risk> tableObservalbeList = treeTableSynchronization(tree);
        table.setItems(tableObservalbeList); //set the table without filters

        tableObservalbeList =  FXCollections.observableArrayList();

        HashMap<String,List<Integer>> listHashMap = new HashMap<>();

        //separate the table into an hashMap with description as key
        for(int i=0;i < table.getItems().size();++i){
            String key = table.getItems().get(i).getDescription();

            List<Integer> value = listHashMap.get(key);
            if(value != null){
                listHashMap.get(key).add(table.getItems().get(i).getRiskId());
            }else{
                List<Integer> obsList = new ArrayList<>();
                obsList.add(table.getItems().get(i).getRiskId());
                listHashMap.put(key,obsList);
            }
        }


        //create a entry in the new table for each entry of the hashmap
        for(Map.Entry<String, List<Integer>> entry : listHashMap.entrySet()) {
            String Key = entry.getKey();
            List<Integer> Value = entry.getValue();
            tableObservalbeList.add(aggregation(Value,Key,"All"));
        }

        System.out.println(tableObservalbeList);

        table.getItems().removeAll(table.getItems());
        table.setItems(tableObservalbeList);
    }

    private void assetView(TableView<Risk> table,TreeView<String> tree){

        table.getItems().removeAll(table.getItems());
        ObservableList<Risk> tableObservalbeList = treeTableSynchronization(tree);
        table.setItems(tableObservalbeList); //set the table without filters

        tableObservalbeList =  FXCollections.observableArrayList();
        HashMap<String,List<Integer>> listHashMap = new HashMap<>();


        //separate the table into an hashMap with description as key
        for(int i=0;i < table.getItems().size();++i){
            String key = table.getItems().get(i).getAsset();

            List<Integer> value = listHashMap.get(key);
            if(value != null){
                listHashMap.get(key).add(table.getItems().get(i).getRiskId());
            }else{
                List<Integer> obsList = new ArrayList<>();
                obsList.add(table.getItems().get(i).getRiskId());
                listHashMap.put(key,obsList);
            }
        }


        //create a entry in the new table for each entry of the hashmap
        for(Map.Entry<String, List<Integer>> entry : listHashMap.entrySet()) {
            String Key = entry.getKey();
            List<Integer> Value = entry.getValue();
            tableObservalbeList.add(aggregation(Value,"All",Key));
        }

        System.out.println(tableObservalbeList);

        table.getItems().removeAll(table.getItems());
        table.setItems(tableObservalbeList);
    }

    //Risk Owner Bottom Row
    private HBox setRiskOwnerHBox(TableView<Risk> table){

        TextField OccurInput = new TextField();
        OccurInput.setPromptText("Expected Number of Occurences Year");
        OccurInput.setMinWidth(200);

        TextField costInput = new TextField();
        costInput.setPromptText("Cost of one Occurence");
        costInput.setMinWidth(200);


        Button updateButton = new Button ("Update");
        updateButton.setOnAction(e -> {
            ownerButtonUpdate(OccurInput,costInput,table);
        });

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);

        hBox.getChildren().addAll(OccurInput,costInput,updateButton);

        return hBox;
    }

    private void ownerButtonUpdate(TextField occurS, TextField costS,TableView<Risk> table){

        try{
            float occurF = Float.parseFloat(occurS.getText());
            float costF = Float.parseFloat(costS.getText());
            float costYear = occurF * costF;

            ObservableList<Risk> selectedRisk = table.getSelectionModel().getSelectedItems();

            selectedRisk.forEach(r -> {
                Risk r2 = r;
                r2.setCostYear(costYear);
                r2.setCost(costF);
                r2.setNrOcuurYear(occurF);


                if(myClient.updateRisk(r2)){ //if was accepted on server

                    Integer i = r.getRiskId();
                    myRisks.get(i).setCostYear(costYear);
                    myRisks.get(i).setCost(costF);
                    myRisks.get(i).setNrOcuurYear(occurF);

                    r.setCostYear(costYear);
                    r.setCost(costF);
                    r.setNrOcuurYear(occurF);

                    table.refresh();

                }else{
                    ConfirmBox.alertBox("Error","There was a problem updating the risk, please try again.");
                }

            });

        }catch (NumberFormatException e){
            ConfirmBox.alertBox("Error","Invalid input, please try again.");
        }

        occurS.clear();
        costS.clear();
    }

    private Button logoutButton(){
        Button logout = new Button("Logout");
        logout.setOnAction(e -> {
            myClient.logout();
            window.close();
        });

        return  logout;
    }

}
