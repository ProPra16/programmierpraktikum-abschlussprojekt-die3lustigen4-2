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
	static boolean writeCode;			// aktuelle Stufe (Step) speichern
	static boolean writeTest;
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
		writeTest = true;
		writeCode = false;
		refactoring = false;
		acutalStep();

	}

	public static StringProperty actualCode(){
		if(writeTest){
			return exerciseTest.content();
		}
		return exerciseCode.content();
	}
	
	public static void acutalStep(){
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
				// TODO hier fehlt noch was mehr
		}
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
		acutalStep();
	}

}
