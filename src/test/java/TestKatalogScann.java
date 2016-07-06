import org.junit.Test;
import userInput.TestInput;

import static org.junit.Assert.assertEquals;

public class TestKatalogScann {

    @Test
    public void constructorTest() {
        TestInput test=new TestInput("Tester");
        assertEquals(test.testcode.getValue(), "Tester");
    }


}
