/**
 * Created by tobias on 10.07.16.
 */

/*********************************************************
 * JUnit Test der einfachen Methoden der Klasse CodeFailure
 ********************************************************/


import org.junit.Test;
import programdata.CodeFailure;

import static org.junit.Assert.assertEquals;

public class CodeFailureTest{

    @Test
    public void dummytest() {
        assertEquals(1,1,0.1);
    }

    @Test
    public void emptyConstructorTest() {
        CodeFailure test=new CodeFailure();
        assertEquals("", test.codeStringProperty().getValue());
        assertEquals("", test.testStringProperty().getValue());
    }


    @Test
    public void constructorTest() {
        CodeFailure test=new CodeFailure("Tester", "testet", 1);
        assertEquals("Tester", test.codeStringProperty().getValue());
        assertEquals("testet", test.testStringProperty().getValue());
    }

    @Test
    public void codeAsStringTest() {
        CodeFailure test=new CodeFailure("Tester", "testet", 1);
        assertEquals(test.codeAsString(), "Tester");
    }

    @Test
    public void addMessageTest(){
        CodeFailure test =new CodeFailure("Testen wir mal", "", 0);
        test.addMessage("die Funktionen.");
        assertEquals(test.codeAsString(), "Testen wir mal\n\ndieFunktionen.");
    }

}

