package Pages;

import Core.*;
import Domain.Risk;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;


public class RiskConfirmation {

    /**
     *
     * @param type 1 for update 2 for delete
     */
    /*public static boolean display(int type, Risk r) {
        Stage window = new Stage();

        //does not allow to change window while this one is not finished
        window.initModality(Modality.APPLICATION_MODAL);
        if(type == 1){
            window.setTitle("Risk Update");
        }
        else{

            window.setTitle("Risk Deletion");
        }
        window.setMinWidth(250);

        Label descriptionLabel = new Label("Description:");
        Label riskOwnerLabel = new Label("Risk Owner:");

        Client myClient = Client.getInstance();
      //  User[] userList = Client.getChildren();
        ComboBox<String> cb = new ComboBox<>();
        cb.setPromptText("Risk Owner");
        /* for(User iu : userList){
            cb.getItems.add(u.getName())
        }
         */



        /*Button button = new Button("submit");

        button.setOnAction(return true);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(descriptionLabel,riskOwnerLabel,cb);
        layout.setAlignment((Pos.CENTER));

        Scene scene = new Scene(layout);
        window .setScene(scene);
        window.showAndWait();
    }

    private void closeProgram(){
        Boolean answer = ConfirmBox.display("Exist Alert Box", "Sure you want to exit?");
        if(answer){
            window.close();
        }
    }*/
}
