/*****************************************
 * Klasse für Speicherung des eigentlichen 
 * Programmablaufs
 *****************************************/

package programdata;

import javafx.beans.property.StringProperty;
import junit.framework.*;
import scenes.Controller;
import scenes.KatalogCreator;
import userInput.CodeInput;
import userInput.TestInput;
import vk.core.api.*;
import vk.core.api.TestFailure;

import java.util.Collection;

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
		Controller.writeHereProperty.setValue(exerciseTest.asString());
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
		compileFolder = CompilerFactory.getCompiler(code, test);
		compileFolder.compileAndRunTests();
		if (compileFolder.getCompilerResult().hasCompileErrors()) {
			CompilerResult compilerResult =compileFolder.getCompilerResult();
			if(writeTest) {
				Collection<CompileError> testerror=compilerResult.getCompilerErrorsForCompilationUnit(test);
				for(CompileError error: testerror) {
					compileFailure.addMessage(error.toString());
				}
			}
			if(refactoring){
				Collection<CompileError> codeerror = compilerResult.getCompilerErrorsForCompilationUnit(code);
				for (CompileError error : codeerror) {
					compileFailure.addMessage(error.toString());
				}
			}
			if(writeCode){
				Collection<CompileError> codeerror = compilerResult.getCompilerErrorsForCompilationUnit(code);
				for(CompileError error: codeerror){
					compileFailure.addMessage(error.toString());
				}
			}
			actualStep();
			return;
		} else {
			if(compileFolder.getTestResult().getNumberOfFailedTests()==0) {
				passed();
				actualStep();
				return;
			}
			Collection<TestFailure> testFehler= compileFolder.getTestResult().getTestFailures();
			for(TestFailure failure: testFehler){
				testFailure.addMessage(failure.getMessage());
			}

		}
	}

	public static void reworkTest(){
	//	Exercise temp = new Exercise
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
