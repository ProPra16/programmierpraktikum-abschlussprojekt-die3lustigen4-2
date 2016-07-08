package scenes;

import javafx.application.Application;
import javafx.stage.Stage;
import programdata.Exercise;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*funktioniert noch nicht weil der return befehl von choosenTask vor dem klicken auf den button ausgeführt und
        choosenExercise somit zum NullObjekt wird. Mir ist irgendwie auch immer noch nicht so richtig klar wo Excercise eigentlich
        aufgerufen werden soll, da es hier nicht wirklich Sinn macht. Wäre es nicht vielleicht schlau Exercise mit allen Funktion static
        zu machen? Die Instanzvariabeln von Excersize können zum Teil auch static gemacht werden oder direkt durch den Inhalt
        des Katalog-Objektes ersetzt werden.*/
        Exercise choosenExcersie = KatalogCreator.chooseTask(primaryStage);
        primaryStage.setTitle("Wähle einen Katalog aus");
        primaryStage.show();
        //choosenExcersie.acutalStep();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
