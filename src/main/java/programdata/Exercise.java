/*****************************************
 * Klasse für Speicherung des eigentlichen 
 * Programmablaufs
 *****************************************/


package programdata;

import javafx.beans.property.StringProperty;
import scenes.Controller;
import userInput.CodeInput;
import userInput.TestInput;
import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.JavaStringCompiler;

public class Exercise {
	boolean writeCode;			// aktuelle Stufe (Step) speichern
	boolean writeTest;
	boolean refactoring;
	
	String exerciseName;		// Name der Aufgabe (für public class..)
	String failure;				// Speicher für zurückgegebene Compilierfehler
	
	TestInput exerciseTest;		// Speicher für Usereingaben (Labelinhalte)
	CodeInput exerciseCode;
	
	CompilationUnit code;	// Übergabe an Bendisposto-Code
	CompilationUnit test;
	
	JavaStringCompiler compileFolder;
	
	
	public Exercise(String exerciseName, String exerciseFramework, String testName, String testFramework){
		this.exerciseName=exerciseName;
		this.writeCode= false;
		this.writeTest=true;
		this.refactoring=false;
		this.code=new CompilationUnit(exerciseName, exerciseFramework, false);
		this.test=new CompilationUnit(testName, testFramework, true);
	}
	
	public StringProperty actualCode(){
		if(writeTest){
			return this.exerciseTest.content();
		}
		return this.exerciseCode.content();
	}
	
	public StringProperty actualStep(){
		//StringProperty temp =new SimpleStringProperty("writeTest");
		if(writeCode)
			Controller.aktuellePhaseProperty.setValue("writeCode");
		else if(refactoring)
			Controller.aktuellePhaseProperty.setValue("refactoring");
		else if(writeTest)
			Controller.aktuellePhaseProperty.setValue("writeTest");
		return Controller.aktuellePhaseProperty;
	}
	
	public void nextStep(){
		if(writeTest){
			this.compileFolder = CompilerFactory.getCompiler(this.code, this.test);
			compileFolder.compileAndRunTests();
			//if(hasC)
				// TODO hier fehlt noch was
		}
	}

		
	public void passed(){
		if((writeTest)&&(!writeCode)&&(!refactoring)){
			this.writeTest=false;
			this.writeCode=true;
			return;
		}
		
		if((!writeTest)&&(writeCode)&&(!refactoring)){
			this.writeCode=false;
			this.refactoring=true;
			return;
		}

		if((!writeTest)&&(!writeCode)&&(refactoring)){
			this.refactoring=false;
			this.writeTest=true;
			return;
		}
	}
}
