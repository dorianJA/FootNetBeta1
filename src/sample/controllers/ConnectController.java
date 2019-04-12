package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;


public class ConnectController   {

    @FXML
    private TextField textName;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField sendTextField;

    @FXML
    private void initialize(){

        sendTextField.setOnKeyPressed(event -> {
            if(event.getCode()== KeyCode.ENTER){

            }
        });

    }



}
