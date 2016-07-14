package programdata;

/**
 * Created by tobias on 13.07.16.
 */


import scenes.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/****************************************************************
 * Klasse zum Abspeichern eines Trackingschritts
 ****************************************************************/

public class TrackStep {
    private final LocalDateTime time;
    public static int testDuration=0;
    public static int codeDuration=0;
    public static int refactorDuration=0;

    private int stepDuration=0;
    private String aktuellePhase;
    private String content;
    private String failures;

    TrackStep(String aktuellePhase, String content, String failures) {
        time = LocalDateTime.now();

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

        stepDuration= min*60 + sec;
        if(Exercise.writeTest){
            testDuration += stepDuration ;
        }
        else if(Exercise.writeCode){
            codeDuration += stepDuration;
        }
        else if(Exercise.refactoring){
            refactorDuration += stepDuration;
        }
        this.aktuellePhase=aktuellePhase;
        this.content=content;
        this.failures=failures;
    }

    ArrayList<String> asStringArrayList() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm:ss");
        ArrayList <String> temp = new ArrayList<>();
        temp.add("Zeit in der Phase zur Testgestaltung: " + testDuration );
        temp.add("Zeit in der Phase zur Codegestaltung: " + codeDuration  );
        temp.add("Zeit in der Refactoring-Phase: " + refactorDuration );
        temp.add("");
        temp.add("Zeit der aktuellen Phase (" + aktuellePhase + "): " + stepDuration + " Sekunden" + "\n");
        temp.add("Aktuelles Datum + Zeit: " + time.format(formatter) + "\n");
        temp.add(content);
        temp.add("");
        temp.add("Compilier-Fehler und fehlgechlagene Tests: ");
        temp.add(failures);
        temp.add("");
        temp.add("");
        return temp;
    }
}
