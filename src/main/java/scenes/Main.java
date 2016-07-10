package scenes;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        KatalogCreator.chooseTask(primaryStage);
        primaryStage.setTitle("Wähle einen Katalog aus");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
