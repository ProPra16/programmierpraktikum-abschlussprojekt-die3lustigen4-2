package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
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
import java.util.ArrayList;
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

    public static void chooseTask(Stage primaryStage) throws IOException {
        GridPane root = new GridPane();
        // /home/leander/workspace/Projekt7/src/katalogFiles/TestKatalog.txt
        System.out.println("Wo ist einer der Kataloge gespeichert? (Pfad angeben mit der datei, also z.B. bla/bla/katalog.txt)");
        Path pa = Paths.get(new Scanner(System.in).next());
        //gibt die Anzahl der dateien im Verzeichnis zurück
        long numberOfCatalogs = Files.list(pa.getParent()).count();

        //Einlesen der Katalogkomponenten wie aufgaben Name oder Beschreibung
        //Zur speicherung von abschnitten die größer als 1 Zeile sind werden Listen verwendet
        //Der Vorteil ist das diese keine feste größe haben wie z.b. ein String Array
        Katalog[] katalogArr = new Katalog[(int) numberOfCatalogs];
        for (int j = 0; j < numberOfCatalogs ; j++) {
            final List<String> f = Files.readAllLines(pa);
            String aufgabenName = f.get(1);
            ArrayList<String> beschreibung = new ArrayList<>();
            ArrayList<String> classHeader = new ArrayList<>();
            ArrayList<String> testHeader = new ArrayList<>();
            int i = 3;
            while (!f.get(i).equals("Classname:")) {
                beschreibung.add(f.get(i));
                i++;
            }
            String className = f.get(++i);
            i = i + 2;
            while (!f.get(i).equals("Testname:")) {
                classHeader.add(f.get(i));
                i++;
            }
            String testName = f.get(++i);
            i = i + 2;
            while (!f.get(i).equals("BabySteps:")) {
                testHeader.add(f.get(i));
                i++;
            }
            boolean babysteps = f.get(++i).equals("true");
            i++;
            boolean timetracking = f.get(++i).equals("true");
            katalogArr[j] = new Katalog(aufgabenName, className, testName, babysteps, timetracking, beschreibung, classHeader, testHeader);
        /* Das hier könnte jemand zu einem Test machen mit asserEquals. Hab das jetzt einfach nur selber zum testen verwendet
        System.out.println(aufgabenName);
        System.out.println(beschreibung.get(0));
        System.out.println(className);
        System.out.println(classHeader.get(0));
        System.out.println(testName);
        System.out.println(testHeader.get(0));
        System.out.println(babysteps);
        System.out.println(timetracking);
        */
        }
        primaryStage.setScene(new Scene(root, 1000, 800));
    }

}
