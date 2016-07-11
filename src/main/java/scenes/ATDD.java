package scenes;

/**
 * Created by tobias on 11.07.16.
 */

import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import programdata.CodeFailure;
import programdata.ExerciseAlternative;

/**************************************************************
 * Klasse für Implementierung von ATDD
 *************************************************************/


public class ATDD {
    public static CodeFailure nextATDDstep(String codeName, StringProperty code,
                                           String testName, StringProperty test,
                                           String acceptanceTestName, StringProperty acceptanceTest){
        CodeFailure failure =new CodeFailure();
        return failure;

    }
    public static void setReworkTest(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(Main.primaryStage);
        alert.setTitle("Test Korrektur");
        alert.setContentText("Korrigiere nun deinen Test.");
        alert.showAndWait();
        ExerciseAlternative.reworkTest();
    }

    public static void manageLabels() {
        if (ExerciseAlternative.writeCode) {
            Controller.codeProperty.setValue(Controller.writeHereProperty.getValue());
        } else if (ExerciseAlternative.writeTest) {
            Controller.testProperty.setValue(Controller.writeHereProperty.getValue());
        } else {
            Controller.codeProperty.setValue(Controller.writeHereProperty.getValue());
        }
    }
}