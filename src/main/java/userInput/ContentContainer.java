/******************************************************
 * Interface zum einfacheren Testen der Klassen CodeInput
 * und TestInput
 ******************************************************/

package userInput;

import javafx.beans.property.StringProperty;

public interface ContentContainer {
	public void setCode(String code);
	public void addCode(String codeToAdd);
	public String asString();
	public StringProperty content();

}
