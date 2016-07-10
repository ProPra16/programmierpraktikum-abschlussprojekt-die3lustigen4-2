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

    public static String nextStep(){
        if(ExerciseAlternative.refactoring) return "Code refactorn";
        if(ExerciseAlternative.writeCode) return "Code schreiben";
        return "Test schreiben";
    }

    public static void stepAnnouncement(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nächster Schritt");
        alert.setContentText(ExerciseAlternative.followingStep);
        alert.showAndWait();
    }



    public static CodeFailure compileTestGenerator(String codeName, StringProperty codeProperty, String testName, StringProperty testProperty){
        CodeFailure compileFailure=new CodeFailure("Compiler Problem:", "", 0);
        CodeFailure testFailure= new CodeFailure("Test Problem:", "", 0);

        CompilationUnit code=new CompilationUnit(codeName, codeProperty.getValue(),false);	// Übergabe an Bendisposto-Code
        CompilationUnit test=new CompilationUnit(testName, testProperty.getValue(), true);

        JavaStringCompiler compileFolder = CompilerFactory.getCompiler(code, test);

        compileFolder.compileAndRunTests();

        if (compileFolder.getCompilerResult().hasCompileErrors()) {
            compileFailure.hasproblem();
            CompilerResult compilerResult = compileFolder.getCompilerResult();
            Collection<CompileError> codeerror;
            if (ExerciseAlternative.writeTest) {
                codeerror = compilerResult.getCompilerErrorsForCompilationUnit(test);
            }else{
                codeerror = compilerResult.getCompilerErrorsForCompilationUnit(code);
            }
            for (CompileError error : codeerror) {
                compileFailure.addMessage(error.toString());
            }
            return compileFailure;
        }else {
            if(compileFolder.getTestResult().getNumberOfFailedTests()==0) {
                return compileFailure;
            }
            testFailure.hastestfailures();
            Collection<TestFailure> testFehler= compileFolder.getTestResult().getTestFailures();
            for(TestFailure failure: testFehler){
                testFailure.addMessage(failure.getMessage());
            }
            return testFailure;
        }
    }

    /*warum nicht einfach die passed-funktion aus ExceriseAlternative verwenden?*/
    public static void passed() {
         if((ExerciseAlternative.writeTest)&&(!ExerciseAlternative.writeCode)&&(!ExerciseAlternative.refactoring)){
             ExerciseAlternative.writeTest=false;
             ExerciseAlternative.writeCode=true;
             Controller.aktuellePhaseProperty.setValue("Aktuelle Phase:\nwriteCode");
             ExerciseAlternative.followingStep="Refactoring";
         } else if((!ExerciseAlternative.writeTest)&&(ExerciseAlternative.writeCode)&&(!ExerciseAlternative.refactoring)){
             ExerciseAlternative.writeCode=false;
             ExerciseAlternative.refactoring=true;
             Controller.aktuellePhaseProperty.setValue("Aktuelle Phase:\nrefactoring");
             ExerciseAlternative.followingStep="Write Test";
         } else if((!ExerciseAlternative.writeTest)&&(!ExerciseAlternative.writeCode)&&(ExerciseAlternative.refactoring)){
             ExerciseAlternative.refactoring=false;
             ExerciseAlternative.writeTest=true;
             Controller.aktuellePhaseProperty.setValue("Aktuelle Phase:\nwriteTest");
             ExerciseAlternative.followingStep="Write Code";
         }
    }
}
