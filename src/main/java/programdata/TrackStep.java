package programdata;

/**
 * Created by tobias on 13.07.16.
 */


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/****************************************************************
 * Klasse zum Abspeichern eines Trackingschritts
 ****************************************************************/

public class TrackStep {

    private String aktuellePhase;
    private String content;
    private String failures;
    public static int refactorDuration;
    private final LocalDateTime time;
    public static int testDuration;
    public static int codeDuration;
    private  int stepDuration;

    TrackStep(String aktuellePhase, String content, String failures, int stepDuration, LocalDateTime time, int testDuration, int codeDuration, int refactorDuration) {
        this.aktuellePhase= aktuellePhase;
        this.content= content;
        this.failures= failures;
        this.time = time;
        this.testDuration = testDuration;
        this.codeDuration = codeDuration;
        this.stepDuration = stepDuration;
        this.refactorDuration = refactorDuration;
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
