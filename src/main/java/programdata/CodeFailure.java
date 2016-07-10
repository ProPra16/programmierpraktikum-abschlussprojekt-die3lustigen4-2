package programdata;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by tobias on 09.07.16.
 */
public class CodeFailure {
    private StringProperty messageForCode;
    private StringProperty messageForTest;
    private IntegerProperty line;
    private boolean problems;
    private int numberOfFailedTests = 0;

    public CodeFailure(String code, String test, int line){
        this.messageForCode=new SimpleStringProperty(code);
        this.messageForTest=new SimpleStringProperty(test);
        this.line=new SimpleIntegerProperty(line);
        this.problems=false;
    }


    public CodeFailure(){
        this.messageForCode=new SimpleStringProperty("");
        this.messageForTest=new SimpleStringProperty("");
        this.line=new SimpleIntegerProperty(0);
        this.problems=false;
    }

    public void setNumberOfFailedTests(int num){
        numberOfFailedTests = num;
    }

    public int getNumberOfFailedTests(){
        return numberOfFailedTests;
    }

    public StringProperty codeStringProperty(){
        return this.messageForCode;
    }

    public StringProperty testStringProperty(){
        return this.messageForTest;
    }

    public String codeAsString(){
        return this.messageForCode.getValue();
    }

    public void addMessage(String message){
        if(this.messageForCode.getValue().equals("")){
            this.messageForCode.setValue(message);
        }else{
            this.messageForCode.setValue(this.messageForCode.getValue()+ "\n\n" + message);
        }
    }

    public boolean problems(){
        return this.problems;
    }

    public void hasProblem(){
        this.problems=true;
    }
}
