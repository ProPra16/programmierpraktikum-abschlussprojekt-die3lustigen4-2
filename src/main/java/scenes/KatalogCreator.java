package scenes;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Font.MONOSPACED;

public class KatalogCreator {

    public static Katalog choosenKatalog;

    public static void chooseTask(Stage primaryStage) throws IOException {
        Katalog[] kataloge = createKatalogArr();
        Button[] auswahlButtons = createButtonArr(kataloge.length, primaryStage, kataloge);
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        for (Button b : auswahlButtons) {
            b.setFont(Font.font(MONOSPACED,13));
            b.setAlignment(Pos.TOP_LEFT);
        }
        root.getChildren().addAll(auswahlButtons);
        primaryStage.setScene(new Scene(root));
    }

    public static Button[] createButtonArr(int length, Stage primaryStage, Katalog[] kataloge){
        Button[] auswahlButtons = new Button[length];
        for (int j = 0; j < length; j++) {
            auswahlButtons[j] = new Button();
            String text = "";
            for (String s: kataloge[j].beschreibung)
                text = text + s + "\n";
            auswahlButtons[j].setText(text);
            auswahlButtons[j].setPrefSize(500, 600/length);

            final Katalog tmpKatalog = kataloge[j];
            auswahlButtons[j].setOnAction(event -> {
                try {
                    choosenKatalog = tmpKatalog;
                    Parent newRoot = FXMLLoader.load(Main.class.getClassLoader().getResource("layout.fxml"));
                    primaryStage.setTitle("TDD by Tobias Quest, Tobias Hojka, Leander Nachtmann, Silvan Habenicht");
                    Scene scene = new Scene(newRoot);
                    primaryStage.setScene(scene);
                    scene.getStylesheets().add(Main.class.getClassLoader().getResource("design.css").toExternalForm());
                    primaryStage.show();
                    primaryStage.setFullScreen(true);
                    primaryStage.setOnCloseRequest(we -> Controller.runTimer= false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return auswahlButtons;
    }

    public static Katalog[] createKatalogArr() throws IOException {
        /** Silvan added FileChooser 2.7.2016 */

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Choose Catalog-Folder");
        alert.setContentText("Bitte wählen Sie den Ordner aus, in dem die Kataloge gespeichert sind.");
        alert.showAndWait();
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
            String minutesForBaby = f.get(++i);
            String firstNumber = "";
            String secondNumber = "";
            int secondsForBabystepps = 0;
            if(!minutesForBaby.equals("")){
                for (int k = 0; k < minutesForBaby.length(); k++) {
                    if(minutesForBaby.charAt(k) != ':')
                        firstNumber = firstNumber + minutesForBaby.charAt(k);
                    else{
                        for (int l = k+1; l < minutesForBaby.length(); l++)
                            secondNumber = secondNumber + minutesForBaby.charAt(l);
                        break;
                    }
                }
                secondsForBabystepps = Integer.parseInt(firstNumber)*60 + Integer.parseInt(secondNumber);
            }
            i++;
            boolean timetracking = f.get(++i).equals("true");
            katalogArr[j] = new Katalog(aufgabenName, className, testName, babysteps, timetracking, beschreibung, classHeader, testHeader, secondsForBabystepps, minutesForBaby);
        }
        return katalogArr;
    }


}
