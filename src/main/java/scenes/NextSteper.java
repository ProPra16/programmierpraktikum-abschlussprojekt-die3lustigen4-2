package scenes;

/**
 * Created by tobias on 10.07.16.
 */


import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import programdata.CodeFailure;
import programdata.ExerciseAlternative;
import userInput.CodeInput;
import userInput.TestInput;
import vk.core.api.*;

import java.util.Collection;

import static programdata.ExerciseAlternative.actualStep;
import static programdata.ExerciseAlternative.compileFailure;
import static programdata.ExerciseAlternative.passed;
import static scenes.Controller.codeProperty;
import static scenes.Controller.testProperty;
import static scenes.Controller.writeHereProperty;

/*********************************************
 * Ziel: Einfache Wartbarkeit der Funktion
 * nextStep durch Auslagerung
 *********************************************/

public class NextSteper {

    public static String nextStep(){
        if(ExerciseAlternative.refactoring) return "Code refactorn";
        if(ExerciseAlternative.writeCode) return "Code schreiben";
        return "Test schreiben";
    }

    public static void stepAnnouncement(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nächster Schritt");
        alert.setContentText(nextStep());
        alert.showAndWait();
    }


    public static CodeFailure compileTestGenerator(String codeName, StringProperty codeProperty, String testName, StringProperty testProperty){
        CodeFailure compileFailure=new CodeFailure("Compiler Problem:", "", 0);
        CodeFailure testFailure= new CodeFailure("Test Problem:", "", 0);

        CompilationUnit code=new CompilationUnit(codeName, codeProperty.getValue(),false);	// Übergabe an Bendisposto-Code
        CompilationUnit test=new CompilationUnit(testName, testProperty.getValue(), true);

        JavaStringCompiler compileFolder;

        compileFolder = CompilerFactory.getCompiler(code, test);
        compileFolder.compileAndRunTests();

        if (compileFolder.getCompilerResult().hasCompileErrors()) {
            compileFailure.hasproblem();
            CompilerResult compilerResult = compileFolder.getCompilerResult();
            Collection<CompileError> codeerror;
            if (ExerciseAlternative.writeTest) {
                codeerror = compilerResult.getCompilerErrorsForCompilationUnit(test);
            }else{
                if(ExerciseAlternative.refactoring) {
                    codeerror = compilerResult.getCompilerErrorsForCompilationUnit(code);
                } else {
                    codeerror = compilerResult.getCompilerErrorsForCompilationUnit(code);
                }
            }
            for (CompileError error : codeerror) {
                compileFailure.addMessage(error.toString());
            }
            return compileFailure;
        }else {
            if(compileFolder.getTestResult().getNumberOfFailedTests()==0) {
            //    passed();
            //    actualStep();
                NextSteper.stepAnnouncement();
                return compileFailure;
            }
            Collection<TestFailure> testFehler= compileFolder.getTestResult().getTestFailures();
            for(TestFailure failure: testFehler){
                testFailure.addMessage(failure.getMessage());
            }
            return testFailure;
        }

    }










}
