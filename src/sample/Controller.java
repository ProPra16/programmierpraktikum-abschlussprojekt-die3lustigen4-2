package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Controller {
    //@FXML
    public Button nextStep = new Button("Nächster Schritt");
    public Button reworkTest = new Button("Test korrigieren");
    public Label testOverview = new Label("Blala");
    public Label codeOverview = new Label("Blala");
    public TextArea writeHere = new TextArea("Write your Code here");
    public HBox buttonBox = new HBox();


    public void start(Stage primaryStage) throws Exception {

    }
}
