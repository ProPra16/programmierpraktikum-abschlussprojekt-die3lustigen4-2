package programdata;

/**
 * Created by tobias on 13.07.16.
 */


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

    public String asString() {
        String temp = "Zeit in der Phase zur Testgestaltung" + testDuration + "\n";
        temp += "Zeit in der Phase zur Codegestaltung" + codeDuration + "\n";
        temp += "Zeit in der Refactoring-Phase" + refactorDuration + "\n";
        temp += "\n";
        temp += "Zeit der aktuellen Phase (" + aktuellePhase + "): " + stepDuration + "\n";
        temp += content + "\n";
        temp += "\n";
        temp += "Compilier-Fehler und fehlgechlagene Tests: \n";
        temp += failures;
        temp += "\n \n";
        return temp;
    }
}
