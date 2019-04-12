package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Person;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticController implements Initializable {
    ObservableList<Person> list = FXCollections.observableArrayList();


    @FXML
    private TableView<Person> tableStat;
    @FXML
    private TableColumn<Person,String> columnName;
    @FXML
    private TableColumn<Person,String> columnGames;
    @FXML
    private TableColumn<Person,String> columnGoals;
    @FXML
    private TextField textName;

    public void stat(ListView<Person> listView){
        list.addAll(listView.getItems());
        tableStat.setItems(list);

       columnName.setCellValueFactory(new PropertyValueFactory<Person,String>("name"));
       columnGames.setCellValueFactory(new PropertyValueFactory<Person,String>("games"));
       columnGoals.setCellValueFactory(new PropertyValueFactory<>("goals"));



    }
    public void change(){
//        tableStat.getSelectionModel().getSelectedItem().setName((textName.getText()));
//        tableStat.refresh();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
