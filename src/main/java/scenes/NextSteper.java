package scenes;

/**
 * Created by tobias on 10.07.16.
 */


import javafx.scene.control.Alert;
import programdata.ExerciseAlternative;

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

}
