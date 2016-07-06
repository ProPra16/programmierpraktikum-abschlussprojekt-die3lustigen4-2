/******************************************************
 * Klasse zum Abspeichern des vom User eingegebenen 
 * Testcode
 ******************************************************/

package userInput;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TestInput {
	public StringProperty testcode;
	
	public TestInput(String code){
		this.testcode=new SimpleStringProperty(code);
	}
	
	public void addCode(String codeToAdd){
		this.testcode.setValue(this.testcode.getValue()+codeToAdd);
	}
	
	public String asString(){
		return this.testcode.getValue();
	}
	
	public StringProperty content(){
		return this.testcode;
	}
}
