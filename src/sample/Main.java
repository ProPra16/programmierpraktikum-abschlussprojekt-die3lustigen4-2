package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller.createTimer();

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");

        primaryStage.setOnCloseRequest(event -> Controller.run = false);

        primaryStage.setScene(new Scene(root, 1000,800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
