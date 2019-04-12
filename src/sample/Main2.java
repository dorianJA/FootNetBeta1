package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main2 extends Application {




    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("forfxml/connectWindow.fxml"));
        primaryStage.setTitle("Conn");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(800);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }
    public static void main(String[] args)  {
        launch(args);

    }


}
