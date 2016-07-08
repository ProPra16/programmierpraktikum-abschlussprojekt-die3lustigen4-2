/*****************************************
 * Klasse für Speicherung des eigentlichen 
 * Programmablaufs
 *****************************************/


package programdata;

import javafx.beans.property.StringProperty;
import scenes.Controller;
import scenes.KatalogCreator;
import userInput.CodeInput;
import userInput.TestInput;
import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.JavaStringCompiler;

public class ExerciseAlternative {
	public static boolean writeCode;			// aktuelle Stufe (Step) speichern
	public static boolean writeTest;
	static boolean refactoring;

	static String failure;				// Speicher für zurückgegebene Compilierfehler

	static TestInput exerciseTest;		// Speicher für Usereingaben (Labelinhalte)
	static CodeInput exerciseCode;

	static CompilationUnit code;	// Übergabe an Bendisposto-Code
	static CompilationUnit test;

	static JavaStringCompiler compileFolder;

	//Hiermit wird das Programm erst richtig gestartet
	public static void start() {
		//testausgabe
		KatalogCreator.choosenKatalog.ausgeben();
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
		actualStep();
	}

	public static StringProperty actualCode(){
		if(writeTest){
			return exerciseTest.content();
		}
		return exerciseCode.content();
	}
	
	public static void actualStep(){
		if(writeCode)
			Controller.aktuellePhaseProperty.setValue("Aktuelle Phase:\nwriteCode");
		else if(refactoring)
			Controller.aktuellePhaseProperty.setValue("Aktuelle Phase:\nrefactoring");
		else if(writeTest)
			Controller.aktuellePhaseProperty.setValue("Aktuelle Phase:\nwriteTest");
	}
	
	public static void nextStep(){
		if(writeTest){
			compileFolder = CompilerFactory.getCompiler(code, test);
			compileFolder.compileAndRunTests();
			//if(hasC)
				// TO-DO hier fehlt noch was mehr
		}
	}

	public static void reworkTest(){
		writeCode = false;
		refactoring = false;
		writeTest = true;
		actualStep();
	}
		
	public static void passed(){
		if((writeTest)&&(!writeCode)&&(!refactoring)){
			Controller.testProperty.setValue(Controller.testPropertyTmp.getValue());
			writeTest=false;
			writeCode=true;
		} else if((!writeTest)&&(writeCode)&&(!refactoring)){
			Controller.codeProperty.setValue(Controller.codePropertyTmp.getValue());
			writeCode=false;
			refactoring=true;
		} else if((!writeTest)&&(!writeCode)&&(refactoring)){
			refactoring=false;
			writeTest=true;
		}
		actualStep();
	}

}
