/******************************************************
 * Interface zum einfacheren Testen der Klassen CodeInput
 * und TestInput
 ******************************************************/

package userInput;

import javafx.beans.property.StringProperty;

interface ContentContainer {
	void addCode(String codeToAdd);
	String asString();
	StringProperty content();

}
