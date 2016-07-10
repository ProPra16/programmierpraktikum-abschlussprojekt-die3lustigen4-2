/*****************************************
 * Klasse für Speicherung des eigentlichen 
 * Programmablaufs
 *****************************************/

package programdata;

import scenes.Controller;
import scenes.KatalogCreator;
import userInput.CodeInput;
import userInput.TestInput;
import vk.core.api.CompilationUnit;
import vk.core.api.JavaStringCompiler;

public class ExerciseAlternative {
	public static boolean writeCode;			// aktuelle Stufe (Step) speichern
	public static boolean writeTest;
	public static boolean refactoring;

//	static String failure;				// Speicher für zurückgegebene Compilierfehler

	public static TestInput exerciseTest;		// Speicher für Usereingaben (Labelinhalte)
	public static CodeInput exerciseCode;
	public static CodeFailure compileFailure;
	public static CodeFailure testFailure;

	static CompilationUnit code;	// Übergabe an Bendisposto-Code
	static CompilationUnit test;

	static JavaStringCompiler compileFolder;

	public static String codeName;
	public static String testName;
	public static String followingStep="write Code";


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
			Controller.aktuellePhaseProperty.setValue("Aktuelle Phase:\nwriteCode");
		else if(refactoring)
			Controller.aktuellePhaseProperty.setValue("Aktuelle Phase:\nrefactoring");
		else if(writeTest)
			Controller.aktuellePhaseProperty.setValue("Aktuelle Phase:\nwriteTest");
	}

	public static void reworkTest(){
		writeCode = false;
		refactoring = false;
		writeTest = true;
		actualStep();
	}
		
	public static void passed(){
		if((writeTest)&&(!writeCode)&&(!refactoring)){
			writeTest=false;
			writeCode=true;
		} else if((!writeTest)&&(writeCode)&&(!refactoring)){
			writeCode=false;
			refactoring=true;
		} else if((!writeTest)&&(!writeCode)&&(refactoring)){
			refactoring=false;
			writeTest=true;
		}
		actualStep();
	}

}
