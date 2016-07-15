package scenes;

import java.util.ArrayList;

public class Katalog {

    final String minutesForBaby;
    final int secondsForBabysteps;
    final private String aufgabenName;
    final private String className;
    final private String testName;
    final boolean babysteps;
    final boolean timetracking;
    final ArrayList<String> beschreibung;
    final public ArrayList<String> classHeader;
    final public ArrayList<String> testHeader;

    Katalog(String aufgabenName, String className, String testName, boolean babysteps, boolean timetracking, ArrayList<String> beschreibung, ArrayList<String> classHeader, ArrayList<String> testHeader, int secondsForBabysteps, String minutesForBaby){
        this.aufgabenName = aufgabenName;
        this.className = className;
        this.testName = testName;
        this.babysteps = babysteps;
        this.timetracking = timetracking;
        this.beschreibung = beschreibung;
        this.classHeader = classHeader;
        this.testHeader = testHeader;
        this.secondsForBabysteps = secondsForBabysteps;
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
        beschreibung.forEach(System.out::println);
        System.out.println(className);
        classHeader.forEach(System.out::println);
        System.out.println(testName);
        testHeader.forEach(System.out::println);
        System.out.println(babysteps);
        System.out.println(secondsForBabysteps);
        System.out.println(minutesForBaby);
        System.out.println(timetracking);
    }
}
