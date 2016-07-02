package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Controller.createTimer();
        Controller.chooseTask(primaryStage);
        primaryStage.setTitle("Wähle einen Katalog aus");
        primaryStage.setOnCloseRequest(event -> Controller.run = false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
