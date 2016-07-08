package scenes;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    public  Button nextStep = new Button();
    public  Button reworkTest = new Button();
    public TextArea testOverview = new TextArea();
    public TextArea codeOverview = new TextArea();
    public TextArea writeHere = new TextArea();
    public HBox buttonBox = new HBox();
    public Label aktuellePhase = new Label();
    public Label rueckmeldung = new Label();

    static volatile SimpleIntegerProperty i = new SimpleIntegerProperty(0);
    static volatile boolean run = true;
    static volatile int k = 0;

    public StringProperty codeProperty = new SimpleStringProperty("CODE");
    public StringProperty testProperty = new SimpleStringProperty("TESTS");

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

    //wird in der fxml datei eingebunden mit: onAction="#setNextStep"
    public void setNextStep(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hallo Welt");
        alert.setContentText("Hier müsste die Buttonfunktionalität eingefügt werden.");
        alert.showAndWait();
    }

    public void setReworkTest(){
        nextStep.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hallo Welt");
            alert.setContentText("Hier müsste die Buttonfunktionalität eingefügt werden.");
            alert.showAndWait();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codeOverview.textProperty().bind(codeProperty);
        testOverview.textProperty().bind(testProperty);
    }
}
