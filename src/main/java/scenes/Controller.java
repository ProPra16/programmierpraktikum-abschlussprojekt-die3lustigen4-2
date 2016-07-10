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
import programdata.CodeFailure;
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

    private String codeName;
    private String testName;

    private Timeline timer;

    //wird in der fxml datei eingebunden mit: onAction="#setNextStep"
    public void setNextStep(){
        manageLabels();
        CodeFailure result = NextSteper.compileTestGenerator(codeName, codeProperty, testName, testProperty);
        if(result.problems()) {
            rueckmeldungProperty.setValue("Rückmeldung:\n" + result.codeAsString());
        }else if(result.testFailures()){
            /* Bei der write Test Phase soll man ja einen Test schreiben der Failed um ihn dann neuen Code zu schreiben der diesen Test erfüllt */
            if(!ExerciseAlternative.writeTest)
                rueckmeldungProperty.setValue(result.codeAsString());
            else{
                rueckmeldungProperty.setValue("Rückmeldung:\nVerändere deinen Code nun so, dass der Test erfüllt wird.");
                giveLabelNewValue();
                NextSteper.stepAnnouncement();
                ExerciseAlternative.passed();
            }
        }else if(ExerciseAlternative.writeTest && !result.testFailures()){
            rueckmeldungProperty.setValue("Rückmeldung:\nDu musst einen Test schreiben der failed!");
        }else{
            rueckmeldungProperty.setValue("Rückmeldung:\nAlles OK! (Compiling and Tests)");
            giveLabelNewValue();
            NextSteper.stepAnnouncement();
            ExerciseAlternative.passed();
        }
    }

    public void giveLabelNewValue() {
        if (ExerciseAlternative.writeCode) {
            reworkTest.setDisable(true);
            writeHereProperty.setValue(codeOverview.getText());
        } else if (ExerciseAlternative.writeTest) {
            reworkTest.setDisable(false);
            writeHereProperty.setValue(codeOverview.getText());
        } else {
            reworkTest.setDisable(true);
            writeHereProperty.setValue(testOverview.getText());
        }
    }

    public void manageLabels() {
        if (ExerciseAlternative.writeCode) {
            codeProperty.setValue(writeHereProperty.getValue());
        } else if (ExerciseAlternative.writeTest) {
            testProperty.setValue(writeHereProperty.getValue());
        } else {
            codeProperty.setValue(writeHereProperty.getValue());
        }
    }

    public void setReworkTest(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test Korrektur");
        alert.setContentText("Korrigiere nun deinen Test.");
        alert.showAndWait();
        writeHereProperty.setValue(testOverview.getText());
        ExerciseAlternative.reworkTest();
    }

    public void setStart(){
        ExerciseAlternative.start();
        timer.playFromStart();
        start.setDisable(true);
        reworkTest.setDisable(true);
        codeProperty.setValue(ExerciseAlternative.exerciseCode.asString());
        testProperty.setValue(ExerciseAlternative.exerciseTest.asString());
        codeName= ExerciseAlternative.codeName;
        testName= ExerciseAlternative.testName;
    }

    //Hier werden die StringPropertys gebinded, sodass wir diese nun von überall aktualisieren können und sich der Text
    // in den TextAreas automatisch ändert.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codeOverview.textProperty().bind(codeProperty);
        testOverview.textProperty().bind(testProperty);
        writeHereProperty.bindBidirectional(writeHere.textProperty());
        aktuellePhase.textProperty().bind(aktuellePhaseProperty);
        rueckmeldung.textProperty().bind(rueckmeldungProperty);

        timer = new Timeline();
        timeSeconds.set(0);
        timer.getKeyFrames().add(
                new KeyFrame(Duration.seconds(Integer.MAX_VALUE),
                        new KeyValue(timeSeconds, Integer.MAX_VALUE)));
        timerLabel.textProperty().bind(timeSeconds.asString());
    }
}
