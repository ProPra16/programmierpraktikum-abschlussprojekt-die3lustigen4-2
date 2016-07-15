package programdata;

/**
 * Created by tobias on 13.07.16.
 */


import scenes.Controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;

/*******************************************
 * Klasse zur Verwaltung der Tracking-Datei
 ******************************************/

public class Tracker {
    private final static Path p= Paths.get("Tracked.txt");
    private static int testDuration=0;
    private static int codeDuration= 0;
    private static int refactorDuration=0;
    private static LocalDateTime startDate;

    /**
     * Könnte gelöscht werden...
     */
    public static void deleteLastTrack() throws IOException {
        Files.delete(p);
    }

    public static void startTrack() throws IOException {
        startDate = LocalDateTime.now();
        Charset charset= Charset.forName("UTF-8");
        ArrayList<String> titel= new ArrayList<>();
        titel.add("Trackingfile");
        titel.add("");
        Files.write (p, titel, charset);
    }

    private static void trackWriter(TrackStep step) throws IOException {
        Charset charset= Charset.forName("UTF-8");
        Files.write(p, step.asStringArrayList(), charset, StandardOpenOption.APPEND);
    }

    private static TrackStep generateStep(boolean stepPassed){
        String aktuellePhase=Controller.aktuellePhaseProperty.getValue();
        String content;
        String failures=Controller.rueckmeldungProperty.getValue();
        LocalDateTime time = LocalDateTime.now();

        int min, sec;
        if(time.getMinute() - startDate.getMinute() >= 0)
            min = time.getMinute() - startDate.getMinute();
        else min = 60 + time.getMinute() - startDate.getMinute();
        if(time.getSecond() - startDate.getSecond() >= 0)
            sec = time.getSecond() - startDate.getSecond();
        else{
            min--;
            sec = 60 + time.getSecond() - startDate.getSecond();
        }

        startDate = time;
        int stepDuration= min*60 + sec;
        if(stepPassed) {
            if (Exercise.writeTest) {
                content = Controller.codeProperty.getValue();
                refactorDuration += stepDuration;
                aktuellePhase = Exercise.followingStep;
            } else if (Exercise.writeCode) {
                content = Controller.testProperty.getValue();
                testDuration += stepDuration;
                aktuellePhase = Exercise.followingStep;
            } else {
                content = Controller.codeProperty.getValue();
                codeDuration += stepDuration;
                aktuellePhase = Exercise.followingStep;
            }
        } else{
            if (Exercise.writeTest) {
                content = Controller.testProperty.getValue();
                testDuration += stepDuration;
            } else if (Exercise.writeCode) {
                content = Controller.codeProperty.getValue();
                codeDuration += stepDuration;
            } else {
                content = Controller.codeProperty.getValue();
                refactorDuration += stepDuration;
            }
        }
        return new TrackStep(aktuellePhase, content, failures, stepDuration, time, testDuration, codeDuration, refactorDuration);
    }

    public static void writeStep(boolean stepPassed) throws IOException {
        trackWriter(generateStep(stepPassed));
    }
}
