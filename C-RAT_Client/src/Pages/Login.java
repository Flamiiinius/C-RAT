package Pages;

/**
 * C-RAT Client
 * author @ThePoog
 * MasterProject @SINTEF & @UiO
 * November 2018
 */

import Core.Client;
import Domain.User;
import javafx.application.Application;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.io.IOException;

/**
 * Based On https://docs.oracle.com/javafx/2/get_started/form.htm
 * Class responsible for the Login InputParameters
 */
public class Login extends Application {
    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage loginStage) {

        window=loginStage;

        loginStage.setTitle("Login");
        loginStage.show();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        loginStage.setScene(scene);


        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button login = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(login);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        login.setOnAction(e->{
            Client myClient = Client.getInstance();
            if(myClient.login(userTextField.getText(),pwBox.getText())){
                System.out.println("Success on Login");
                try {
                    UserPage userPage = new UserPage();
                    String[] args = {};
                    userPage.start(loginStage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }else{
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Username or Password invalid");
                pwBox.clear();
            }
        });
    }

    private void closeProgram(){
        Boolean answer = ConfirmBox.display("Exist Alert Box", "Sure you want to exit?");
        if(answer){
            window.close();
        }
    }
}
