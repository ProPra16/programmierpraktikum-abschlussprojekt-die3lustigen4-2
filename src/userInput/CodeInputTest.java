/*********************************************************
 * JUnit Test der einfachen Methoden der Klasse CodeInput
 ********************************************************/


package userInput;

import static org.junit.Assert.*;

import org.junit.Test;

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
