package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/forfxml/sample.fxml"));
        primaryStage.setTitle("Balance Team");
        primaryStage.setMinHeight(680);
        primaryStage.setMinWidth(915);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();


    }


    public static void main(String[] args)  {
        launch(args);

    }
}
