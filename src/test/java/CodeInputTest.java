/*********************************************************
 * JUnit Test der einfachen Methoden der Klasse CodeInput
 ********************************************************/


import org.junit.Test;
import userInput.TestInput;

import static org.junit.Assert.assertEquals;

public class CodeInputTest{

	@Test
	public void dummytest() {
		assertEquals(1,1,0.1);
	}
	
	@Test
	public void constructorTest() {
		TestInput test=new TestInput("Tester");
		assertEquals(test.testcode.getValue(), "Tester");
	}
	
	@Test
	public void asStringTest() {
		TestInput test=new TestInput("Tester");
		assertEquals(test.asString(), "Tester");
	}
	
	@Test
	public void addCodeTest() {
		TestInput test=new TestInput("Tester");
		test.addCode(" erfolgreich");
		assertEquals(test.asString(), "Tester erfolgreich");
	}
}
