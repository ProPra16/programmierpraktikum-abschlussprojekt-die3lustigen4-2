/*****************************************
 * Klasse für Speicherung des eigentlichen 
 * Programmablaufs
 *****************************************/


package programdata;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import userInput.CodeInput;
import userInput.TestInput;

public class Exercise {
	boolean writeCode;			// aktuelle Stufe (Step) speichern
	boolean writeTest;
	boolean refactoring;
	
	String exerciseName;		// Name der Aufgabe (für public class..)
	String failure;				// Speicher für zurückgegebene Compilierfehler
	
	TestInput exerciseTest;		// Speicher für Usereingaben (Labelinhalte)
	CodeInput exerciseCode;
	
	
	public StringProperty actualCode(){
		if(writeTest){
			return this.exerciseTest.content();
		}
		return this.exerciseCode.content();
	}
	
	public StringProperty acutalStep(){
		StringProperty temp =new SimpleStringProperty("writeTest");
		if(this.writeCode){
			temp.setValue("writeCode");
		}
		if(this.refactoring){
			temp.setValue("refactoring");
		}
		return temp;
	}
	
	public void nextStep(){
		//TODO Aufruf des Compilertool von Bendisposto (this.actualCode)
		if((writeTest==true)&&(writeCode==false)&&(refactoring==false)){
			this.writeTest=false;
			this.writeCode=true;
			return;
		}
		
		if((writeTest==false)&&(writeCode==true)&&(refactoring==false)){
			this.writeCode=false;
			this.refactoring=true;
			return;
		}

		if((writeTest==false)&&(writeCode==false)&&(refactoring==true)){
			this.refactoring=false;
			this.writeTest=true;
			return;
		}
	}
}
