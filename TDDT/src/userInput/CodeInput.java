/******************************************************
 * Klasse zum Abspeichern des vom User eingegebenen
 * Quellcodes
 ******************************************************/

package userInput;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CodeInput implements ContentContainer{
	StringProperty quellcode;
	
	public CodeInput(String code){
		this.quellcode=new SimpleStringProperty(code);
	}
	
	public void addCode(String codeToAdd){
		this.quellcode.setValue(this.quellcode.getValue()+codeToAdd);
	}
	
	public String asString(){
		return this.quellcode.getValue();
	}
}
