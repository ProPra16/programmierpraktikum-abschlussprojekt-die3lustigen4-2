package programdata;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.JavaStringCompiler;

public class JENS_CODE_EXAMPLE {

    public static void test() {
        /* Tobias hatte um eine Beispiel für Jenscode gewünscht wegen der Imports. */
        JavaStringCompiler newUnit = CompilerFactory.getCompiler();
        CompilationUnit test = new CompilationUnit("Test.java","public static void main(String[] args) {\n" +
                                                                                                    "        \n" +
                                                                                                    "    } ", false);
    }

}
