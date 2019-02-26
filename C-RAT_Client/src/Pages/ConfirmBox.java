package Pages;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox{
    static boolean answer; // the program does not allow for 2 pop-up boxes open at the same time.

    public static boolean display(String title,String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label(message);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e ->{
            answer = true;
            window.close();
        });
        noButton.setOnAction(e ->{
            answer = false;
            window.close();
        });

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(yesButton,noButton);
        hbox.setPadding(new Insets(10,10,10,10));
        hbox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(label,hbox);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

        return answer;
    }

    public static boolean display(String title,String message,String button1, String button2){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label(message);

        Button yesButton = new Button(button1);
        Button noButton = new Button(button2);

        yesButton.setOnAction(e ->{
            answer = true;
            window.close();
        });
        noButton.setOnAction(e ->{
            answer = false;
            window.close();
        });


        //layout
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(yesButton,noButton);
        hbox.setPadding(new Insets(10,10,10,10));
        hbox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(label,hbox);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

        return answer;
    }

    public static void alertBox(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label(message);

        Button yesButton = new Button("OK");

        yesButton.setOnAction(e ->{
            answer = true;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(label,yesButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

}
