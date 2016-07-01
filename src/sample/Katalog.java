package sample;

import java.util.ArrayList;

public class Katalog {

    String aufgabenName;
    String className;
    String testName;
    boolean babysteps;
    boolean timetracking;
    ArrayList<String> beschreibung;
    ArrayList<String> classHeader;
    ArrayList<String> testHeader;

    public Katalog(String aufgabenName, String className, String testName, boolean babysteps, boolean timetracking, ArrayList<String> beschreibung, ArrayList<String> classHeader, ArrayList<String> testHeader){
        this.aufgabenName = aufgabenName;
        this.className = className;
        this.testName = testName;
        this.babysteps = babysteps;
        this.timetracking = timetracking;
        this.beschreibung = beschreibung;
        this.classHeader = classHeader;
        this.testHeader = testHeader;
    }
}
