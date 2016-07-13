package scenes;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import programdata.CodeFailure;
import programdata.Exercise;
import programdata.Tracker;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public Label timerLabelSeconds1 = new Label();
    public Label babyLabel = new Label();

    public ImageView picture = new ImageView();
    private double anglerest = 0;

    private static StringProperty codeProperty = new SimpleStringProperty("");
    private static StringProperty testProperty = new SimpleStringProperty("");
    public static StringProperty writeHereProperty = new SimpleStringProperty("");

    public static StringProperty aktuellePhaseProperty = new SimpleStringProperty("");
    public static StringProperty rueckmeldungProperty = new SimpleStringProperty("");

    private String codeName;
    private String testName;

    private Timeline timerSeconds;
    private int counter;

    private SimpleIntegerProperty timeSeconds;
    private SimpleIntegerProperty timeMinutes;

    public Timeline getTimerSeconds(){
        return timerSeconds;
    }

    //wird in der fxml datei eingebunden mit: onAction="#setNextStep"k
    public void setNextStep() throws IOException {
        /** Hab jetzt so einiges durch Probiert aber so weit ich das sehe funktioniert nun alles einwandfrei selbst der
         * ReworkTest Button. Zum Code ausprobieren würde ich den RealTestKatalog empfehlen. */

        /** Bei der write Test Phase soll man ja einen Test schreiben der Failed,
         * um dann neuen Code zu schreiben der diesen Test erfüllt. */

        manageLabels();
        /**************************************/
        //Trackingergänzungen
        if(!KatalogCreator.choosenKatalog.withBabysteps()) {
            Tracker.writeStep();
        }
        /**************************************/
        CodeFailure result = NextSteper.compileTestGenerator(codeName, codeProperty, testName, testProperty);
        if(result.problems()) {
            rueckmeldungProperty.setValue(result.codeAsString());
        }else if(result.getNumberOfFailedTests() > 0){
            if(!Exercise.writeTest)
                rueckmeldungProperty.setValue(result.codeAsString());
            else if(result.getNumberOfFailedTests() > 1){
                rueckmeldungProperty.setValue("Es muss genau 1 Test fehlschlagen!");
            }else{
                rueckmeldungProperty.setValue("Verändere deinen Code nun so, dass der Test erfüllt wird.");
                giveLabelNewValue();
                Exercise.passed();
                timerSeconds.stop();
                safeDate();
                changeView();
                NextSteper.stepAnnouncement();
                resetTimer();
            }
        }else if(Exercise.writeTest && result.getNumberOfFailedTests() == 0){
            rueckmeldungProperty.setValue("Du musst einen Test schreiben der fehlschlägt!");
        }else{
            rueckmeldungProperty.setValue("Alles OK! (Compiling and Tests)");
            giveLabelNewValue();
            Exercise.passed();
            timerSeconds.stop();
            changeView();
            safeDate();
            NextSteper.stepAnnouncement();
            resetTimer();

        }
    }

    /**Hier eine Funktion mit der man das aktuelle Datum+Zeit abspeichern kann. Ich weiß bloß noch nicht wie man es
     * hinkriegt die Daten in Statistics.txt zu speichern ohne den vollständigen Pfad anzugeben.
     * Ich kann erst später weiter arbeiten aber wollte das kurz pushen falls das hier jemand verwenden möchte.
     *
     * @throws IOException
     */
    private void safeDate() throws IOException {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        /*
        final Path p =Paths.get("/home/leander/workspace/Projekt7-/src/main/resources/Statistic.txt");
        File statistics = new File("/home/leander/workspace/Projekt7-/src/main/resources/Statistic.txt");
        Files.write(p, time.format(formatter).getBytes());
        */
        System.out.println(time.format(formatter));
        System.out.println(time.format(formatter2));
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

    private void giveLabelNewValue() {
        if (Exercise.writeCode) {
            reworkTest.setDisable(true);
            writeHereProperty.setValue(codeOverview.getText());
        } else if (Exercise.writeTest) {
            reworkTest.setDisable(false);
            writeHereProperty.setValue(codeOverview.getText());
        } else {
            reworkTest.setDisable(true);
            writeHereProperty.setValue(testOverview.getText());
        }
    }

    private void manageLabels() {
        if (Exercise.writeCode) {
            codeProperty.setValue(writeHereProperty.getValue());
        } else if (Exercise.writeTest) {
            testProperty.setValue(writeHereProperty.getValue());
        } else {
            codeProperty.setValue(writeHereProperty.getValue());
        }
    }

    public void setReworkTest(){
        /**************************************/
        //Trackingergänzungen
        if(!KatalogCreator.choosenKatalog.withBabysteps()) {
            Tracker.writeStep();
        }
        /**************************************/
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(Main.primaryStage);
        alert.setTitle("Test Korrektur");
        alert.setContentText("Korrigiere nun deinen Test.");
        alert.showAndWait();
        writeHereProperty.setValue(testOverview.getText());
        resetTimer();
        Exercise.reworkTest();
    }

    public void setStart(){
        Exercise.start();

        /**************************************/
        //Trackingergänzungen
        if(!KatalogCreator.choosenKatalog.withBabysteps()) {
            Tracker.startTrack();
        }
        /**************************************/

        resetTimer();
        start.setDisable(true);
        nextStep.setDisable(false);
        codeProperty.setValue(Exercise.exerciseCode.asString());
        testProperty.setValue(Exercise.exerciseTest.asString());
        codeName= Exercise.codeName;
        testName= Exercise.testName;
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
        final int finalTimeLimitSecondsLastTime = timeLimitSecondsLastTime;
        final int finalTimeLimitSeconds = timeLimitSeconds;

        timerSeconds.getKeyFrames().add(
                new KeyFrame(Duration.seconds(timeLimitSeconds),
                        new KeyValue(timeSeconds, timeLimitSeconds)));
        timerSeconds.setOnFinished(event ->{
            counter++;
            if(counter == timeLimitMinutes && finalTimeLimitSecondsLastTime > 0){
                timeMinutes.set(timeMinutes.getValue() + 1);
                newTimer(timeLimitMinutes, finalTimeLimitSecondsLastTime);
            }else if(counter < timeLimitMinutes){
                timeMinutes.set(timeMinutes.getValue() + 1);
                newTimer(timeLimitMinutes, finalTimeLimitSecondsLastTime + finalTimeLimitSeconds);
            } else jumpOneStepBack();
        });
        timerSeconds.playFromStart();
    }

    private void jumpOneStepBack() {
        if (Exercise.writeCode) {
            Exercise.writeTest = true;
            Exercise.writeCode = false;
            Exercise.refactoring = false;
            reworkTest.setDisable(true);
            writeHereProperty.setValue(testOverview.getText());
            rueckmeldungProperty.setValue("Schreibe einen Test der failed.");
        } else if (Exercise.writeTest) {
            Exercise.writeTest = false;
            Exercise.writeCode = false;
            Exercise.refactoring = true;
            reworkTest.setDisable(true);
            rueckmeldungProperty.setValue("Verbessere deinen Code.");
            writeHereProperty.setValue(codeOverview.getText());
        }
        Exercise.actualStep();
        resetTimer();
        changeView();
    }

    private void changeView(){

        double angle = 0;

        if(Exercise.refactoring) {
            writeHere.setStyle("-fx-text-fill: black;");
            angle = 120;
        }
        else if(Exercise.writeCode){
            writeHere.setStyle("-fx-text-fill: green;");
            angle = 240;
        }
        else if(Exercise.writeTest){
            writeHere.setStyle("-fx-text-fill: red;");
            angle = 0;
        }
        double add = anglerest;
        anglerest = angle;
        if(angle != 0)
            anglerest = 360 - angle;
        angle +=add;
        if(angle >= 360)
            angle = angle - 360;
        if(angle <= -360)
            angle = angle + 360;
        if(angle > 180)
            angle = angle - 360;
        if(angle < -180)
            angle = 360+angle;

        RotateTransition rt = new RotateTransition(Duration.millis(2000), picture);
        rt.setByAngle(angle);
        rt.play();

    }

}
