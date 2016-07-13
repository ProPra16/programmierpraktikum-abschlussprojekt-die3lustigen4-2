package programdata;

/**
 * Created by tobias on 13.07.16.
 */


import java.util.ArrayList;

/****************************************************************
 * Klasse zum Abspeichern eines Trackingschritts
 ****************************************************************/

public class TrackStep {
    int nowTime;
    int testDuration;
    int codeDuration;
    int refactorDuration;

    int stepDuration;
    String aktuellePhase;
    String content;
    String failures;

    public TrackStep(){
    }

    public TrackStep(int nowTime, int testDuration, int codeDuration, int refactorDuration, int stepDuration, String aktuellePhase, String content, String failures) {
        this.nowTime=nowTime;
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
