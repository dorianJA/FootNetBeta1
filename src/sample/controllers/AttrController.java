package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Person;



import java.net.URL;
import java.util.ResourceBundle;

public class AttrController implements Initializable {
    @FXML
    private Label name;
    @FXML
    private ImageView imageView;
    @FXML
    private Label number;
    @FXML
    private Label atrHit;
    @FXML
    private Label atrContr;
    @FXML
    private Label atrDef;
    @FXML
    private Label atrAvr;

private Image brazil = new Image("sample/assets/brazil_640.png");
private Image russia = new Image("sample/assets/russia_640.png");
private Image neth = new Image("sample/assets/netherlands_640.png");

    public void attributes(String nameF,Integer contr,Integer hit,Integer def,Integer avr,Integer numb ){
        name.setText(nameF);
        atrContr.setText(contr.toString());
        atrHit.setText(hit.toString());
        atrDef.setText(def.toString());
        atrAvr.setText(avr.toString());
        number.setText(numb.toString());
    }

    public void countryView(String cntry){
        switch (cntry){
            case "rus":  imageView.setImage(russia);
            break;
            case "bra": imageView.setImage(brazil);
            break;
            case "neth": imageView.setImage(neth);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
