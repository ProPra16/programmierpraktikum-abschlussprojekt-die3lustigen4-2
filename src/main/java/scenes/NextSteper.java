package scenes;

/**
 * Created by tobias on 10.07.16.
 */


import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import programdata.CodeFailure;
import programdata.ExerciseAlternative;
import vk.core.api.*;

import java.util.Collection;

/*********************************************
 * Ziel: Einfache Wartbarkeit der Funktion
 * nextStep durch Auslagerung
 *********************************************/

public class NextSteper {

    public static void stepAnnouncement(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(Main.primaryStage);
        alert.setTitle("Nächster Schritt");
        alert.setContentText(ExerciseAlternative.followingStep);
        alert.showAndWait();
    }

    public static CodeFailure compileTestGenerator(String codeName, StringProperty codeProperty, String testName, StringProperty testProperty){
        CodeFailure compileFailure=new CodeFailure("Compiler Problem: ", "", 0);
        CodeFailure testFailure= new CodeFailure("Test Problem: ", "", 0);

        CompilationUnit code=new CompilationUnit(codeName, codeProperty.getValue(), false);	// Übergabe an Bendisposto-Code
        CompilationUnit test=new CompilationUnit(testName, testProperty.getValue(), true);

        JavaStringCompiler compileFolder = CompilerFactory.getCompiler(code, test);

        compileFolder.compileAndRunTests();

        if (compileFolder.getCompilerResult().hasCompileErrors()) {
            compileFailure.hasProblem();
            CompilerResult compilerResult = compileFolder.getCompilerResult();
            Collection<CompileError> codeError;
            if (ExerciseAlternative.writeTest) {
                codeError = compilerResult.getCompilerErrorsForCompilationUnit(test);
            }else{
                codeError = compilerResult.getCompilerErrorsForCompilationUnit(code);
            }
            for (CompileError error : codeError) {
                compileFailure.addMessage(error.toString());
            }
            return compileFailure;
        }else {
            if(compileFolder.getTestResult().getNumberOfFailedTests()==0) {
                return compileFailure;
            }
            testFailure.setNumberOfFailedTests(compileFolder.getTestResult().getNumberOfFailedTests());
            Collection<TestFailure> testFehler= compileFolder.getTestResult().getTestFailures();
            for(TestFailure failure: testFehler){
                testFailure.addMessage(failure.getMessage());
            }
            return testFailure;
        }
    }
}
