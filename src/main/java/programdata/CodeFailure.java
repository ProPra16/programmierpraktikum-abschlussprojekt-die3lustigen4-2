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
        messageForCode=new SimpleStringProperty(code);
        this.messageForTest=new SimpleStringProperty(test);
        this.line=new SimpleIntegerProperty(line);
        this.problems=false;
    }


    public CodeFailure(){
        messageForCode=new SimpleStringProperty("");
        messageForTest=new SimpleStringProperty("");
        line=new SimpleIntegerProperty(0);
        problems=false;
    }

    public void setNumberOfFailedTests(int num){
        numberOfFailedTests = num;
    }

    public int getNumberOfFailedTests(){
        return numberOfFailedTests;
    }

    public StringProperty codeStringProperty(){
        return messageForCode;
    }

    public StringProperty testStringProperty(){
        return messageForTest;
    }

    public String codeAsString(){
        return messageForCode.getValue();
    }

    public void addMessage(String message){
        if(messageForCode.getValue().equals("")){
            messageForCode.setValue(message);
        }else{
            messageForCode.setValue(messageForCode.getValue()+ "\n\n" + message);
        }
    }

    public boolean problems(){
        return problems;
    }

    public void hasProblem(){
        problems=true;
    }
}
