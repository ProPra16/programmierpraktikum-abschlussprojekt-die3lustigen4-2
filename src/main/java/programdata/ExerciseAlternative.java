/*****************************************
 * Klasse für Speicherung des eigentlichen 
 * Programmablaufs
 *****************************************/

package programdata;

import scenes.Controller;
import scenes.KatalogCreator;
import userInput.CodeInput;
import userInput.TestInput;

public class ExerciseAlternative {
	public static boolean writeCode;			// aktuelle Stufe (Step) speichern
	public static boolean writeTest;
	public static boolean refactoring;

	public static TestInput exerciseTest;		// Speicher für Usereingaben (Labelinhalte)
	public static CodeInput exerciseCode;

	public static String codeName;
	public static String testName;
	public static String followingStep="Write Code";

	//Hiermit wird das Programm erst richtig gestartet
	public static void start() {
		String completeClassHeader = "";
		String completeTestHeader = "";
		for (String s: KatalogCreator.choosenKatalog.classHeader)
			completeClassHeader = completeClassHeader + s +"\n";
		for (String s: KatalogCreator.choosenKatalog.testHeader)
			completeTestHeader = completeTestHeader + s +"\n";

		writeTest = true;
		writeCode = false;
		refactoring = false;
		exerciseCode = new CodeInput(completeClassHeader);
		exerciseTest = new TestInput(completeTestHeader);
		codeName = KatalogCreator.choosenKatalog.getClassName();
		testName = KatalogCreator.choosenKatalog.getTestName();
		Controller.writeHereProperty.setValue(exerciseTest.asString());
		actualStep();																	//Markierung
	}
	
	public static void actualStep(){
		if(writeCode)
			Controller.aktuellePhaseProperty.setValue("Write New Code");
		else if(refactoring)
			Controller.aktuellePhaseProperty.setValue("Refactoring");
		else if(writeTest)
			Controller.aktuellePhaseProperty.setValue("Write a failing Test");
	}

	public static void reworkTest(){
		writeCode = false;
		refactoring = false;
		writeTest = true;
		followingStep = "Write Code";
		actualStep();
	}
		
	public static void passed(){
		if((writeTest)&&(!writeCode)&&(!refactoring)){
			writeTest=false;
			writeCode=true;
			ExerciseAlternative.followingStep="Refactoring";
		} else if((!writeTest)&&(writeCode)&&(!refactoring)){
			writeCode=false;
			refactoring=true;
			ExerciseAlternative.followingStep="Write Test";
		} else if((!writeTest)&&(!writeCode)&&(refactoring)){
			refactoring=false;
			writeTest=true;
			ExerciseAlternative.followingStep="Write Code";
		}
		actualStep();
	}

}
