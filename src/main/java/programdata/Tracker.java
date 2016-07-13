package programdata;

/**
 * Created by tobias on 13.07.16.
 */


import javafx.animation.Timeline;
import scenes.Controller;

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
    final static Path p= Paths.get("Tracked.txt");
    public static int nowTime;
    public static int testDuration;
    public static int codeDuration;
    public static int refactorDuration;
    public static Timeline timeline;

    public static void startTrackTimer(){
        timeline= new Timeline();
    }

    public static int getTimerSeconds(){
        return (int) timeline.getCurrentTime().toSeconds();
    }

    public static void deleteLastTrack(){
        try{
            Files.delete(p);
        }catch(Exception e){
        }
    }

    public static void startTrack(){
        Charset charset= Charset.forName("UTF-8");
        ArrayList<String> titel= new ArrayList<>();
        titel.add("Trackingfile");
        titel.add("");
        try {
            Files.write (p, titel, charset);
        }catch(Exception e){
        }
        startTrackTimer();
    }

    public static void trackWriter(TrackStep step){
        Charset charset= Charset.forName("UTF-8");
        try {
            Files.write(p, step.asStringArrayList(), charset, StandardOpenOption.APPEND);
        }catch(Exception e){
        }
    }

    public static TrackStep generateStep(){

        String aktuellePhase=Controller.aktuellePhaseProperty.getValue();
        String content=Controller.writeHereProperty.getValue();
        String failures=Controller.rueckmeldungProperty.getValue();
        TrackStep temp = new TrackStep(aktuellePhase, content, failures);
        return temp;
    }

    public static void writeStep(){
        trackWriter(generateStep());
    }
}
