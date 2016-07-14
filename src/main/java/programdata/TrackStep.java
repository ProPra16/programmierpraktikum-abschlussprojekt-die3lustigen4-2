package programdata;

/**
 * Created by tobias on 13.07.16.
 */


import java.util.ArrayList;

/****************************************************************
 * Klasse zum Abspeichern eines Trackingschritts
 ****************************************************************/

class TrackStep {
    private int testDuration=0;
    private int codeDuration=0;
    private int refactorDuration=0;

    private int stepDuration=0;
    private String aktuellePhase;
    private String content;
    private String failures;

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
    TrackStep(String aktuellePhase, String content, String failures) {
        int nowTime = Tracker.getTimerSeconds();
        stepDuration= nowTime -(testDuration+codeDuration+ refactorDuration);
        if(Exercise.writeTest){
            testDuration+= nowTime;
        }
        else if(Exercise.writeCode){
            codeDuration+= nowTime;
        }
        else if(Exercise.refactoring){
            refactorDuration+= nowTime;
        }
        this.aktuellePhase=aktuellePhase;
        this.content=content;
        this.failures=failures;
    }

    ArrayList<String> asStringArrayList() {
        ArrayList <String> temp = new ArrayList<>();
        temp.add("Zeit in der Phase zur Testgestaltung: " + testDuration );
        temp.add("Zeit in der Phase zur Codegestaltung: " + codeDuration  );
        temp.add("Zeit in der Refactoring-Phase: " + refactorDuration );
        temp.add("");
        temp.add("Zeit der aktuellen Phase (" + aktuellePhase + "): " + stepDuration + "\n");
        temp.add(content);
        temp.add("");
        temp.add("Compilier-Fehler und fehlgechlagene Tests: ");
        temp.add(failures);
        temp.add("");
        temp.add("");
        return temp;
    }
}
