package programdata;

/**
 * Created by tobias on 13.07.16.
 */


import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*******************************************
 * Klasse zur Verwaltung der Tracking-Datei
 ******************************************/
public class Tracker {
    final static Path p= Paths.get("Tracked.txt");
    public static int testDuration;
    public static int codeDuration;
    public static int refactorDuration;

    public static void deleteLastTrack(){
        try{
            Files.delete(p);
        }catch(Exception e){
        }
    }

    public static void trackWriter(TrackStep step){
        Charset charset= Charset.forName("UTF-8");
        try {
            Files.write(p, step.asStringArrayList(), charset);
        }catch(Exception e){
        }
    }


    public TrackStep generateTrackStep(){
        TrackStep temp = new TrackStep();
        return temp;
    }
}
