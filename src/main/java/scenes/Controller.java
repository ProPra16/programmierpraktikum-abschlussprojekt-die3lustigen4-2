package scenes;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import programdata.ExerciseAlternative;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    public Button nextStep = new Button();
    public Button reworkTest = new Button();
    public Button start = new Button();
    public TextArea testOverview = new TextArea();
    public TextArea codeOverview = new TextArea();
    public TextArea writeHere = new TextArea();
    public HBox buttonBox = new HBox();
    public Label aktuellePhase = new Label();
    public Label rueckmeldung = new Label();
    public Label timerLabel = new Label();

    SimpleIntegerProperty timeSeconds = new SimpleIntegerProperty(0);

    public static StringProperty codeProperty = new SimpleStringProperty("CODE");
    public static StringProperty testProperty = new SimpleStringProperty("TESTS");
    public static StringProperty writeHereProperty = new SimpleStringProperty("");

    public static StringProperty aktuellePhaseProperty = new SimpleStringProperty("Aktuelle Phase:");
    public static StringProperty rueckmeldungProperty = new SimpleStringProperty("Rückmeldung:");


    //wird in der fxml datei eingebunden mit: onAction="#setNextStep"
    public void setNextStep(){
        String schritt;
        if(ExerciseAlternative.writeCode){
            reworkTest.setDisable(true);
            codeProperty.setValue(writeHereProperty.getValue());
            writeHereProperty.setValue(codeOverview.getText());
            schritt = "Refactorn";
        } else if(ExerciseAlternative.writeTest){
            testProperty.setValue(writeHereProperty.getValue());
            reworkTest.setDisable(false);
            writeHereProperty.setValue(codeOverview.getText());
            schritt = "Code schreiben";
        } else{
            codeProperty.setValue(writeHereProperty.getValue());
            writeHereProperty.setValue(testOverview.getText());
            reworkTest.setDisable(true);
            schritt = "Test schreiben";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hallo Welt");
        alert.setContentText("Nächster Schritt: " + schritt);
        alert.showAndWait();
        ExerciseAlternative.passed();
    }

    public void setReworkTest(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hallo Welt");
        alert.setContentText("Korrigiere nun deinen Test.");
        alert.showAndWait();
        writeHereProperty.setValue(testOverview.getText());
        ExerciseAlternative.reworkTest();
    }

    public void setStart(){
        ExerciseAlternative.start();
        start.setDisable(true);
        reworkTest.setDisable(true);
        codeProperty.setValue(ExerciseAlternative.exerciseCode.asString());
        testProperty.setValue(ExerciseAlternative.exerciseTest.asString());
    }

    //Hier werden die StringPropertys gebinded sodass wir diese nun von überall aktualisieren können und sich der Text
    // in den TextAreas automatisch ändert ich hab jetzt auch mal beide Text
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codeOverview.textProperty().bind(codeProperty);
        testOverview.textProperty().bind(testProperty);
        writeHereProperty.bindBidirectional(writeHere.textProperty());
        aktuellePhase.textProperty().bind(aktuellePhaseProperty);
        rueckmeldung.textProperty().bind(rueckmeldungProperty);

        Timeline timer = new Timeline();
        timeSeconds.set(0);
        timer.getKeyFrames().add(
                new KeyFrame(Duration.seconds(Integer.MAX_VALUE),
                        new KeyValue(timeSeconds, Integer.MAX_VALUE)));
        timer.playFromStart();
        timerLabel.textProperty().bind(timeSeconds.asString());
    }
}
