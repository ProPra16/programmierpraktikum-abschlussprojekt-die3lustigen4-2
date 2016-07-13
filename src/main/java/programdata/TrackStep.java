package programdata;

/**
 * Created by tobias on 13.07.16.
 */


import java.util.ArrayList;

/****************************************************************
 * Klasse zum Abspeichern eines Trackingschritts
 ****************************************************************/

public class TrackStep {
    int testDuration;
    int codeDuration;
    int refactorDuration;

    int stepDuration;
    String aktuellePhase;
    String content;
    String failures;

    public TrackStep(){
    }

    public TrackStep(int testDuration, int codeDuration, int refactorDuration, int stepDuration, String aktuellePhase, String content, String failures) {
        this.testDuration=testDuration;
        this.codeDuration=codeDuration;
        this.refactorDuration=refactorDuration;
        this.stepDuration=stepDuration;
        this.aktuellePhase=aktuellePhase;
        this.content=content;
        this.failures=failures;
    }

    public ArrayList<String> asStringArrayList() {
        ArrayList <String> temp = new ArrayList<String>();
        temp.add("Zeit in der Phase zur Testgestaltung" + testDuration + "\n");
        temp.add("Zeit in der Phase zur Codegestaltung" + codeDuration + "\n");
        temp.add("Zeit in der Refactoring-Phase" + refactorDuration + "\n");
        temp.add("\n");
        temp.add("Zeit der aktuellen Phase (" + aktuellePhase + "): " + stepDuration + "\n");
        temp.add(content + "\n");
        temp.add("\n");
        temp.add("Compilier-Fehler und fehlgechlagene Tests: \n");
        temp.add(failures);
        temp.add("\n \n");
        return temp;
    }
}
