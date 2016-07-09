package scenes;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class KatalogCreator {

    public static Katalog choosenKatalog;

    public static void chooseTask(Stage primaryStage) throws IOException {
        Katalog[] kataloge = createKatalogArr();
        Button[] auswahlButtons = createButtonArr(kataloge.length, primaryStage, kataloge);
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(35);
        root.setVgap(35);

        for (int j = 0; j < auswahlButtons.length; j++)
            root.add(auswahlButtons[j],0,j);
        primaryStage.setScene(new Scene(root, 1000, 800));
    }

    public static Button[] createButtonArr(int length, Stage primaryStage, Katalog[] kataloge){
        Button[] auswahlButtons = new Button[length];
        for (int j = 0; j < length; j++) {
            auswahlButtons[j] = new Button();
            String text = "";
            for (String s: kataloge[j].beschreibung)
                text = text + s + "\n";
            auswahlButtons[j].setText(text);
            auswahlButtons[j].setPrefSize(800, 600/length);

            final Katalog tmpKatalog = kataloge[j];
            /*
            String completeClassHeader = "";
            for (String s: tmpKatalog.classHeader)
                completeClassHeader = completeClassHeader + s +"\n";
            final String finalCompleteClassHeader = completeClassHeader;

            String completeTestHeader = "";
            for (String s: tmpKatalog.testHeader)
                completeTestHeader = completeTestHeader + s +"\n";
            final String finalCompleteTestHeader = completeTestHeader;
            */

            auswahlButtons[j].setOnAction(event -> {
                try {
                    //choosenExercise = new Exercise(tmpKatalog.aufgabenName, finalCompleteClassHeader, tmpKatalog.testName, finalCompleteTestHeader);
                    choosenKatalog = tmpKatalog;
                    Parent newRoot = FXMLLoader.load(Main.class.getClassLoader().getResource("layout.fxml"));
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
        return auswahlButtons;
    }

    public static Katalog[] createKatalogArr() throws IOException {
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
