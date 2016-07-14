package programdata;

/**
 * Created by tobias on 13.07.16.
 */


import javafx.animation.Timeline;
import scenes.Controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/*******************************************
 * Klasse zur Verwaltung der Tracking-Datei
 ******************************************/

public class Tracker {
    private final static Path p= Paths.get("Tracked.txt");
    public static int nowTime;
    public static int testDuration;
    public static int codeDuration;
    public static int refactorDuration;
    private static Timeline timeline;

    private static void startTrackTimer(){
        timeline= new Timeline();
    }

    static int getTimerSeconds(){
        return (int) timeline.getCurrentTime().toSeconds();
    }

    public static void deleteLastTrack() throws IOException {
        Files.delete(p);
    }

    public static void startTrack() throws IOException {
        Charset charset= Charset.forName("UTF-8");
        ArrayList<String> titel= new ArrayList<>();
        titel.add("Trackingfile");
        titel.add("");
        Files.write (p, titel, charset);
        startTrackTimer();
    }

    private static void trackWriter(TrackStep step) throws IOException {
        Charset charset= Charset.forName("UTF-8");
        Files.write(p, step.asStringArrayList(), charset, StandardOpenOption.APPEND);
    }

    private static TrackStep generateStep(){

        String aktuellePhase=Controller.aktuellePhaseProperty.getValue();
        String content=Controller.writeHereProperty.getValue();
        String failures=Controller.rueckmeldungProperty.getValue();
        return new TrackStep(aktuellePhase, content, failures);
    }

    public static void writeStep() throws IOException {
        trackWriter(generateStep());
    }
}
