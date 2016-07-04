package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller.createTimer();
        Controller.chooseTask(primaryStage);
        primaryStage.setTitle("Wähle einen Katalog aus");
        primaryStage.setOnCloseRequest(event -> Controller.run = false);

        primaryStage.show();

        /** Beendet Timer-Thread, wenn das Programm geschlossen wird*/
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Controller.run = false;
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
