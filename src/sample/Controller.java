package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Controller {

    public Button nextStep = new Button("Nächster Schritt");
    public Button reworkTest = new Button("Test korrigieren");
    public Label testOverview = new Label("Blala");
    public Label codeOverview = new Label("Blala");
    public TextArea writeHere = new TextArea("Write your Code here");
    public HBox buttonBox = new HBox();
    public static Label timerLabel = new Label();
    public Label aktuellePhase = new Label();
    public Label rueckmeldung = new Label();

    static volatile SimpleIntegerProperty i = new SimpleIntegerProperty(0);
    static volatile boolean run = true;
    static volatile int k = 0;


    public static void createTimer(){
        run=true;
        timerLabel.textProperty().bind(i.asString());

        Thread t = new Thread(() -> {
            while (run) {
                k++;
                Platform.runLater(() -> i.set(k));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public static void chooseTask(Stage primaryStage){
        GridPane root = new GridPane();
        root.setPrefSize(1000, 800);
        System.out.println("Wo ist der Katalog gespeichert? (Pfad angeben)");
        final Path pa = Paths.get(new Scanner(System.in).next());
        try {
            final List<String> f = Files.readAllLines(pa);
        } catch (IOException e) {
            System.out.println("didn't work: " + e);
        }
    }

}
