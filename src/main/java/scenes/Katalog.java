package scenes;

import java.util.ArrayList;

public class Katalog {

    String minutesForBaby;
    int secondsForBabystepps;
    private String aufgabenName;
    private String className;
    private String testName;
    boolean babysteps;
    boolean timetracking;
    ArrayList<String> beschreibung;
    public ArrayList<String> classHeader;
    public ArrayList<String> testHeader;

    Katalog(String aufgabenName, String className, String testName, boolean babysteps, boolean timetracking, ArrayList<String> beschreibung, ArrayList<String> classHeader, ArrayList<String> testHeader, int secondsForBabystepps, String minutesForBaby){
        this.aufgabenName = aufgabenName;
        this.className = className;
        this.testName = testName;
        this.babysteps = babysteps;
        this.timetracking = timetracking;
        this.beschreibung = beschreibung;
        this.classHeader = classHeader;
        this.testHeader = testHeader;
        this.secondsForBabystepps = secondsForBabystepps;
        this.minutesForBaby = minutesForBaby;
    }

    public String getClassName(){
        return className;
    }

    public String getTestName(){
        return testName;
    }

    public void ausgeben(){
        System.out.println(aufgabenName);
        for (String s: beschreibung)
            System.out.println(s);
        System.out.println(className);
        for (String s: classHeader)
            System.out.println(s);
        System.out.println(testName);
        for (String s: testHeader)
            System.out.println(s);
        System.out.println(babysteps);
        System.out.println(secondsForBabystepps);
        System.out.println(minutesForBaby);
        System.out.println(timetracking);
    }

    public boolean withBabysteps(){
        return babysteps;
    }
}
