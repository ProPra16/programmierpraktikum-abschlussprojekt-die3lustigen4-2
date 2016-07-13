package programdata;

/**
 * Created by tobias on 13.07.16.
 */


import scenes.Controller;

import java.util.ArrayList;

/****************************************************************
 * Klasse zum Abspeichern eines Trackingschritts
 ****************************************************************/

public class TrackStep {
    int nowTime;
    int testDuration=0;
    int codeDuration=0;
    int refactorDuration=0;

    int stepDuration=0;
    String aktuellePhase;
    String content;
    String failures;

    public TrackStep(){
    }

    /*
    public TrackStep(int nowTime, int testDuration, int codeDuration, int refactorDuration, int stepDuration, String aktuellePhase, String content, String failures) {
        this.nowTime= (int) Controller.getTimerSeconds();
        this.testDuration=testDuration;
        this.codeDuration=codeDuration;
        this.refactorDuration=refactorDuration;
        this.stepDuration=stepDuration;
        this.aktuellePhase=aktuellePhase;
        this.content=content;
        this.failures=failures;
    }
    */
    public TrackStep(String aktuellePhase, String content, String failures) {
        this.nowTime= Tracker.getTimerSeconds();
        this.stepDuration=nowTime-(testDuration+codeDuration+ refactorDuration);
        if(Exercise.writeTest){
            testDuration+=nowTime;
        }
        if(Exercise.writeCode){
            codeDuration+=nowTime;
        }
        if(Exercise.refactoring){
            refactorDuration+=nowTime;
        }
        this.aktuellePhase=aktuellePhase;
        this.content=content;
        this.failures=failures;
    }

    public ArrayList<String> asStringArrayList() {
        ArrayList <String> temp = new ArrayList<String>();
        temp.add("Zeit in der Phase zur Testgestaltung: " + testDuration );
        temp.add("Zeit in der Phase zur Codegestaltung: " + codeDuration  );
        temp.add("Zeit in der Refactoring-Phase: " + refactorDuration );
        temp.add("");
        temp.add("Zeit der aktuellen Phase (" + aktuellePhase + "): " + stepDuration + "\n");
        temp.add(content );
        temp.add("");
        temp.add("Compilier-Fehler und fehlgechlagene Tests: ");
        temp.add(failures);
        temp.add("");
        temp.add("");
        return temp;
    }
}
