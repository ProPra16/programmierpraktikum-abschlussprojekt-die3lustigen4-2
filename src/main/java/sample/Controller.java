package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    public Button nextStep = new Button();
    public Button reworkTest = new Button();
    public TextArea testOverview = new TextArea();
    public TextArea codeOverview = new TextArea();
    public TextArea writeHere = new TextArea();
    public HBox buttonBox = new HBox();
    public Label aktuellePhase = new Label();
    public Label rueckmeldung = new Label();

    static volatile SimpleIntegerProperty i = new SimpleIntegerProperty(0);
    static volatile boolean run = true;
    static volatile int k = 0;

    /** Timer startet in separatem Fenster */
    public static void createTimer(){
        run = true;
        //timerLabel.textProperty().bind(i.asString());

        Stage timer = new Stage();

        timer.setTitle("Timer");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Label time = new Label();
        grid.add(time, 1, 1);
        time.setText(k+"");


        Thread t = new Thread(() -> {
            while (run) {
                Platform.runLater(() -> {
                time.setText(k+"");
                k++;
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        Scene tscene = new Scene(grid, 100, 100);
        timer.setScene(tscene);
        timer.show();
    }

    public static void chooseTask(Stage primaryStage) throws IOException {
        Katalog[] kataloge = createKatalogArr();
        Button[] auswahlButtons = new Button[kataloge.length];
        for (int j = 0; j < kataloge.length; j++) {
            auswahlButtons[j] = new Button();
            String text = "";
            for (String s: kataloge[j].beschreibung)
                text = text + s + "\n";
            auswahlButtons[j].setText(text);
            auswahlButtons[j].setPrefSize(800, 600/kataloge.length);
            auswahlButtons[j].setOnAction(event -> {
                try {
                    Parent newRoot = FXMLLoader.load(Main.class.getClassLoader().getResource("sample.fxml"));
                    primaryStage.setTitle("TDD by Tobias Quest, Tobias Hojka, Leander Nachtmann, Silvan Habenicht");
                    Scene scene = new Scene(newRoot, 1000,800);
                    primaryStage.setScene(scene);
                    scene.getStylesheets().add(Main.class.getClassLoader().getResource("design.css").toExternalForm());
                    primaryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(35);
        root.setVgap(35);
        for (int j = 0; j < auswahlButtons.length; j++)
            root.add(auswahlButtons[j],0,j);
        primaryStage.setScene(new Scene(root, 1000, 800));
    }

    public static Katalog[] createKatalogArr() throws IOException {
        // /home/leander/workspace/Projekt7/src/katalogFiles
        //System.out.println("Wo sind die Kataloge gespeichert? (Pfad angeben)");

        /** Silvan added FileChooser 2.7.2016 */

        DirectoryChooser dialog = new DirectoryChooser();
        dialog.setTitle("Choose Catalog-Folder");
        File folder = dialog.showDialog(new Stage());
        String path = folder.getAbsolutePath();

        File[] listOfFiles = folder.listFiles();
        //Einlesen der Katalogkomponenten wie aufgaben Name oder Beschreibung
        //Zur speicherung von abschnitten die größer als 1 Zeile sind werden Listen verwendet
        //Der Vorteil ist das diese keine feste größe haben wie z.b. ein String Array
        Katalog[] katalogArr = new Katalog[listOfFiles.length];
        for (int j = 0; j < listOfFiles.length ; j++) {
            final List<String> f = Files.readAllLines(Paths.get(path + "/" + listOfFiles[j].getName()));
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
        }
        return katalogArr;
    }

}