package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller.createTimer();
        Controller.chooseTask(primaryStage);
        primaryStage.setTitle("Wähle einen Katalog aus");
        primaryStage.show();
        System.out.print("test");
        /** Beendet Timer-Thread, wenn das Programm geschlossen wird*/
        primaryStage.setOnCloseRequest(we -> Controller.run = false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
