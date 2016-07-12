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
    public Label aktuellePhase = new Label();
    public Label rueckmeldung = new Label();
    public Label timerLabelMinutes = new Label();
    public Label timerLabelSeconds = new Label();
    public Label babyLabel = new Label();

    public static StringProperty codeProperty = new SimpleStringProperty("CODE");
    public static StringProperty testProperty = new SimpleStringProperty("TESTS");
    public static StringProperty writeHereProperty = new SimpleStringProperty("");

    public static StringProperty aktuellePhaseProperty = new SimpleStringProperty("");
    public static StringProperty rueckmeldungProperty = new SimpleStringProperty("");


    private String codeName;
    private String testName;

    private Timeline timerSeconds;
    private int counter;

    private SimpleIntegerProperty timeSeconds;
    private SimpleIntegerProperty timeMinutes;

    //wird in der fxml datei eingebunden mit: onAction="#setNextStep"k
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
                timerSeconds.stop();
                resetTimer();
            }
        }else if(ExerciseAlternative.writeTest && result.getNumberOfFailedTests() == 0){
            rueckmeldungProperty.setValue("Du musst einen Test schreiben der fehlschlägt!");
        }else{
            rueckmeldungProperty.setValue("Alles OK! (Compiling and Tests)");
            giveLabelNewValue();
            NextSteper.stepAnnouncement();
            ExerciseAlternative.passed();
            timerSeconds.stop();
            resetTimer();
        }
    }

    private void resetTimer() {
        if(!aktuellePhaseProperty.get().equals("Refactoring")) {
            if (KatalogCreator.choosenKatalog.babysteps) {
                babyLabel.setText("Du hast " + KatalogCreator.choosenKatalog.minutesForBaby + " Minuten für jede Phase, außer der Refactor Phase!");
                if (KatalogCreator.choosenKatalog.secondsForBabystepps > 60) {
                    if(KatalogCreator.choosenKatalog.secondsForBabystepps % 60 == 0)
                        createTimer(KatalogCreator.choosenKatalog.secondsForBabystepps / 60, 60);
                    else
                        createTimer(KatalogCreator.choosenKatalog.secondsForBabystepps / 60, KatalogCreator.choosenKatalog.secondsForBabystepps % 60 + 60);
                }else
                    createTimer(0, KatalogCreator.choosenKatalog.secondsForBabystepps);
            } else
                createTimer(Integer.MAX_VALUE, 60);
        }
        else{
            timeSeconds.set(0);
            timeMinutes.set(0);
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
            if(KatalogCreator.choosenKatalog.babysteps) {
                //runTimer = false;
                //timerLabel.setText("Keine Zeitbegrenzung.");
            }
            codeProperty.setValue(writeHereProperty.getValue());
        } else if (ExerciseAlternative.writeTest) {
            testProperty.setValue(writeHereProperty.getValue());
        } else {
            codeProperty.setValue(writeHereProperty.getValue());
            //if(KatalogCreator.choosenKatalog.babysteps)
                //startTimer(KatalogCreator.choosenKatalog.secondsForBabystepps);
        }
    }

    public void setReworkTest(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(Main.primaryStage);
        alert.setTitle("Test Korrektur");
        alert.setContentText("Korrigiere nun deinen Test.");
        alert.showAndWait();
        writeHereProperty.setValue(testOverview.getText());
        ExerciseAlternative.reworkTest();
    }

    public void setStart(){
        ExerciseAlternative.start();
        resetTimer();
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

    private void createTimer(int timeLimitMinutes, int timeLimitSeconds) {
        timeSeconds = new SimpleIntegerProperty(0);
        timeMinutes = new SimpleIntegerProperty(0);
        if(timeLimitMinutes > 0)
            counter = 0;
        else counter = -1;
        newTimer(timeLimitMinutes ,timeLimitSeconds);
        timerLabelSeconds.textProperty().bind(timeSeconds.asString());
        timerLabelMinutes.textProperty().bind(timeMinutes.asString());
    }

    private void newTimer(int timeLimitMinutes, int timeLimitSeconds) {
        timerSeconds = new Timeline();
        timeSeconds = new SimpleIntegerProperty(0);
        timerLabelSeconds.textProperty().bind(timeSeconds.asString());
        int timeLimitSecondsLastTime = 0;
        if(timeLimitSeconds > 60){
            timeLimitSecondsLastTime = timeLimitSeconds - 60;
            timeLimitSeconds = timeLimitSeconds - timeLimitSecondsLastTime;
        }
        timerSeconds.getKeyFrames().add(
                new KeyFrame(Duration.seconds(timeLimitSeconds),
                        new KeyValue(timeSeconds, timeLimitSeconds)));
        timerSeconds.setOnFinished(event ->{
            counter++;
            if(counter != timeLimitMinutes){
                timeMinutes.set(timeMinutes.getValue() + 1);
                newTimer(timeLimitMinutes, timeLimitSeconds);
            } else jumpOneStepBack();
        });
        timerSeconds.playFromStart();
    }

    private void jumpOneStepBack() {
        if (ExerciseAlternative.writeCode) {
            ExerciseAlternative.writeTest = true;
            ExerciseAlternative.writeCode = false;
            ExerciseAlternative.refactoring = false;
            reworkTest.setDisable(true);
            writeHereProperty.setValue(testOverview.getText());
            rueckmeldungProperty.setValue("Schreibe einen Test der failed.");
        } else if (ExerciseAlternative.writeTest) {
            ExerciseAlternative.writeTest = false;
            ExerciseAlternative.writeCode = false;
            ExerciseAlternative.refactoring = true;
            reworkTest.setDisable(true);
            rueckmeldungProperty.setValue("Verbessere deinen Code.");
            writeHereProperty.setValue(codeOverview.getText());
        }
        ExerciseAlternative.actualStep();
        resetTimer();
    }

}
