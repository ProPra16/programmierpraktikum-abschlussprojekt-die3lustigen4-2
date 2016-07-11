package scenes;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import programdata.CodeFailure;
import programdata.ExerciseAlternative;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    public Button nextStep = new Button();
    public Button reworkTest = new Button();
    public Button start = new Button();
    public TextArea testOverview = new TextArea();
    public TextArea codeOverview = new TextArea();
    public TextArea writeHere = new TextArea();
    public Label aktuellePhase = new Label();
    public Label rueckmeldung = new Label();
    public Label timerLabel = new Label();

    public static StringProperty codeProperty = new SimpleStringProperty("CODE");
    public static StringProperty testProperty = new SimpleStringProperty("TESTS");
    public static StringProperty writeHereProperty = new SimpleStringProperty("");

    public static StringProperty aktuellePhaseProperty = new SimpleStringProperty("");
    public static StringProperty rueckmeldungProperty = new SimpleStringProperty("");

    private String codeName;
    private String testName;

    public static boolean runTimer = false;
    private int minutes = 0;
    private int seconds = 0;

    //wird in der fxml datei eingebunden mit: onAction="#setNextStep"
    public void setNextStep(){
        /** Hab jetzt so einiges durch Probiert aber so weit ich das sehe funktioniert nun alles einwandfrei selbst der
         * ReworkTest Button. Zum Code ausprobieren würde ich den RealTestKatalog empfehlen. */

        /** Bei der write Test Phase soll man ja einen Test schreiben der Failed,
         * um dann neuen Code zu schreiben der diesen Test erfüllt. */
        manageLabels();
        CodeFailure result = NextSteper.compileTestGenerator(codeName, codeProperty, testName, testProperty);
        if(result.problems()) {
            rueckmeldungProperty.setValue(result.codeAsString());
        }else if(result.getNumberOfFailedTests() > 0){
            if(!ExerciseAlternative.writeTest)
                rueckmeldungProperty.setValue(result.codeAsString());
            else if(result.getNumberOfFailedTests() > 1){
                rueckmeldungProperty.setValue("Es muss genau 1 Test fehlschlagen!");
            }else{
                rueckmeldungProperty.setValue("Verändere deinen Code nun so, dass der Test erfüllt wird.");
                giveLabelNewValue();
                NextSteper.stepAnnouncement();
                ExerciseAlternative.passed();
            }
        }else if(ExerciseAlternative.writeTest && result.getNumberOfFailedTests() == 0){
            rueckmeldungProperty.setValue("Du musst einen Test schreiben der fehlschlägt!");
        }else{
            rueckmeldungProperty.setValue("Alles OK! (Compiling and Tests)");
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
        startTimer();
        start.setDisable(true);
        nextStep.setDisable(false);
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

        reworkTest.setDisable(true);
        nextStep.setDisable(true);

    }

    void startTimer(){

        runTimer = true;

        Thread t = new Thread(() -> {

            while (runTimer) {
                Platform.runLater(() -> {
                    timerLabel.setText(new DecimalFormat("00").format(minutes) + ":" + new DecimalFormat("00").format(seconds));
                    seconds++;
                    if(seconds % 60 == 0){
                        minutes++;
                        seconds = 0;
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        t.start();

    }

}
