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

    /*******************************
     * Das hier sind die Variablen die für den Graphen benötigt werden,
     */
    private static int testDuration=0;
    private static int codeDuration= 0;
    private static int refactorDuration=0;

    /**
     * Könnte gelöscht werden...
     */
    public static void deleteLastTrack() throws IOException {
        Files.delete(p);
    }

    public static void startTrack() throws IOException {
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

    private static TrackStep generateStep(){
        String aktuellePhase=Controller.aktuellePhaseProperty.getValue();
        String content=Controller.writeHereProperty.getValue();
        String failures=Controller.rueckmeldungProperty.getValue();
        LocalDateTime time = LocalDateTime.now();

        int min, sec;
        if(time.getMinute() - Controller.startDate.getMinute() >= 0)
            min = time.getMinute() - Controller.startDate.getMinute();
        else min = 60 + time.getMinute() - Controller.startDate.getMinute();
        if(time.getSecond() - Controller.startDate.getSecond() >= 0)
            sec = time.getSecond() - Controller.startDate.getSecond();
        else{
            min--;
            sec = 60 + time.getSecond() - Controller.startDate.getSecond();
        }

        Controller.startDate = time;

        int stepDuration= min*60 + sec;
        if(Exercise.writeTest){
            testDuration += stepDuration ;
        }
        else if(Exercise.writeCode){
            codeDuration += stepDuration;
        }
        else if(Exercise.refactoring){
            refactorDuration += stepDuration;
        }
        return new TrackStep(aktuellePhase, content, failures, stepDuration, time, testDuration, codeDuration, refactorDuration);
    }

    public static void writeStep() throws IOException {
        trackWriter(generateStep());
    }
}
