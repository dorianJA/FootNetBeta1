package sample.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


import org.controlsfx.control.Notifications;
import sample.Person;
import sample.connections.networkpack.TCPConnection;
import sample.connections.networkpack.TCPConnectionListener;
import sample.interfaces.CollectionPeopleImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.Collections;


public class Controller implements TCPConnectionListener {

    private CollectionPeopleImpl people = new CollectionPeopleImpl();
    private ObservableList<Person> allPlayer = FXCollections.observableArrayList();


    //TCP
    @FXML
    private TextArea textArea;
    @FXML
    private TextField sendTextField;
    @FXML
    private TextField textName;

    private static final String IP_ADDR = "10.10.0.147";
    private static final int PORT = 8189;

    private TCPConnection connection;




    @FXML
    private ListView<Person> listView;

    @FXML
    private ListView<Person>  teamOneView;

    @FXML
    private  ListView<Person> teamTwoView;

    @FXML
    private  ListView<Person> teamThreeView;
    @FXML
    private Button balanceButton;
    @FXML
    private Label teamOneAvr;
    @FXML
    private Label teamTwoAvr;
    @FXML
    private Label teamThreeAvr;
    @FXML
    private ListView<Person> playerReady;
    @FXML
    private Button buttonReset;


    @FXML
    private ImageView imagePlus;
    @FXML
    private ImageView imageMinus;
    @FXML
    private ImageView imageStat;

    private JAXBContext contextTemp;
    private JAXBContext contextPlayerReady;
    private JAXBContext contextTeamOne;
    private JAXBContext contextTeamTwo;
    private JAXBContext contextTeamThree;
    private File fileReadyPlayer = new File("\\\\adminsrv\\System.dst\\foot\\playerReadyXML.xml");
    private File fileTeamOne = new File("\\\\adminsrv\\System.dst\\foot\\teamOneXML.xml");
    private File fileTeamTwo = new File("\\\\adminsrv\\System.dst\\foot\\teamTwoXML.xml");
    private File fileTeamThree = new File("\\\\adminsrv\\System.dst\\foot\\teamThreeXML.xml");
    private File file = new File("\\\\adminsrv\\System.dst\\foot\\tempXML.xml");
    private CollectionPeopleImpl persons = new CollectionPeopleImpl();
    private CollectionPeopleImpl teamOnelist = new CollectionPeopleImpl();
    private CollectionPeopleImpl teamTwolist = new CollectionPeopleImpl();
    private CollectionPeopleImpl teamThreelist = new CollectionPeopleImpl();
    private Image image = new Image("/sample/assets/qu2.png");



    @FXML
    private void initialize(){
        textArea.setEditable(false);
        textArea.setWrapText(true);
//        textName.setText("Введите_Имя");



        imagePlus.setOnMouseClicked(event -> {
            if (listView.getSelectionModel().getSelectedItem() != null && !listView.getSelectionModel().getSelectedItem().isReady() && textName.getText().equals("max10")) {  // перенести из одного листа в другой
//                listView.getSelectionModel().getSelectedItem().setReady(true);
//                playerReady.getItems().add(listView.getSelectionModel().getSelectedItem());
                try {
                    contextTemp = JAXBContext.newInstance(Person.class);
                    Marshaller marshaller = contextTemp.createMarshaller();
                    marshaller.marshal(listView.getSelectionModel().getSelectedItem(), file);
                }catch (JAXBException e){
                    e.printStackTrace();
                }
                connection.sendString("IndexP: "+listView.getSelectionModel().getSelectedIndex());
                connection.sendString(listView.getSelectionModel().getSelectedItem()+" добавлен");
            }
        });

        imageMinus.setOnMouseClicked(event -> {
            if(playerReady.getSelectionModel().getSelectedItem() !=null && textName.getText().equals("max10")) {
//                 playerReady.getSelectionModel().getSelectedItem().setReady(false);
//                connection.sendPerson(playerReady.getSelectionModel().getSelectedItem());
                try {

                    contextTemp = JAXBContext.newInstance(Person.class);
                    Marshaller marshaller = contextTemp.createMarshaller();
                    marshaller.marshal(playerReady.getSelectionModel().getSelectedItem(), file);
                }catch (JAXBException e){
                    e.printStackTrace();
                }
                connection.sendString("IndexM: "+playerReady.getSelectionModel().getSelectedIndex());
                connection.sendString(playerReady.getSelectionModel().getSelectedItem() + " не играет");
            }
        });
        imageStat.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/forfxml/statistics.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Statistics");
                StatisticController connectController = loader.getController();
                connectController.stat(listView);
                stage.setMinWidth(150);
                stage.setMinHeight(300);
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        });


        balanceButton.setVisible(false);
        buttonReset.setVisible(false);





        try {
            connection = new TCPConnection(this,IP_ADDR,PORT);

        } catch (IOException e) {
            printMsg("Connection exception: " +e);
        }
        sendTextField.setOnKeyPressed(event -> {
            if(event.getCode()== KeyCode.ENTER){
                String msg = sendTextField.getText();

                if(msg.equals("")) return;
                sendTextField.setText(null);
                connection.sendString(textName.getText()+ ": " + msg);
                if(msg.equals("!b")){
                    allP();
                    connection.sendString("balance");

                }
//                else if(msg.equals("!c")){
//
//                }
            }
        });



        people.fillPeopleData();
        listView.setItems(people.getListPeople());


        EventHandler<MouseEvent> mouseEventEventHandler = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                try {
                    if(e.getClickCount() == 2 && listView.getSelectionModel().getSelectedItem()!=null) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/forfxml/attribute.fxml"));
                        Parent root = loader.load();
                        AttrController attrController = loader.getController();
                        attrController.attributes(listView.getSelectionModel().getSelectedItem().getName(),
                                listView.getSelectionModel().getSelectedItem().getBallControl(),
                                listView.getSelectionModel().getSelectedItem().getHit(),
                                listView.getSelectionModel().getSelectedItem().getDefense(),
                                listView.getSelectionModel().getSelectedItem().avr(),
                                listView.getSelectionModel().getSelectedItem().getPlayerNumber());

                        attrController.countryView(listView.getSelectionModel().getSelectedItem().getCountry());
                        Stage stage = new Stage();
                        stage.setTitle("Attributes");
                        stage.setMinWidth(150);
                        stage.setMinHeight(300);
                        stage.setResizable(false);
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.show();
                    }
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        };
        listView.addEventHandler(MouseEvent.MOUSE_CLICKED,mouseEventEventHandler);



    }

    private synchronized void printMsg(String msg){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                textArea.setStyle("-fx-text-fill: green ;");
                textArea.appendText(msg+"\n");
                textArea.positionCaret(textArea.getLength());

                if(msg.startsWith("IndexP: ")){
                    String[] indexP = msg.split("\\s");
                    addAll(Integer.parseInt(indexP[1]));
                }
                else if(msg.startsWith("IndexM: ")){
                    String[] indexM = msg.split("\\s");
                    minusAll(Integer.parseInt(indexM[1]));
                }
                else if(msg.equals("balance")){
                    addAllTeams();
                }


                try {

                    String[] str = msg.split(":");

                     if (str[1].equals(" !c")) {
                        reset();
                    }
                    else if(str[1].equals(" !n")){
                         Notifications notifications = Notifications.create()
                                 .title("Оповещение: сегодня футбол")
                                 .text("Играешь сегодня? Поставь + ")
                                 .graphic(new ImageView(image))
                                 .position(Pos.CENTER)
                                 .darkStyle();


                         notifications.show();
                     }



                }catch (ArrayIndexOutOfBoundsException e) {
                }
                }

        });
    }

    private synchronized void addAllTeams(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    contextTeamOne = JAXBContext.newInstance(CollectionPeopleImpl.class);
                    Unmarshaller unmarshaller = contextTeamOne.createUnmarshaller();
                    teamOnelist = (CollectionPeopleImpl) unmarshaller.unmarshal(fileTeamOne);
                    teamOneView.getItems().setAll(teamOnelist.getListPeople());

                    contextTeamTwo = JAXBContext.newInstance(CollectionPeopleImpl.class);
                    Unmarshaller unmarshaller1 = contextTeamTwo.createUnmarshaller();
                    teamTwolist = (CollectionPeopleImpl) unmarshaller1.unmarshal(fileTeamTwo);
                    teamTwoView.getItems().setAll(teamTwolist.getListPeople());

                    contextTeamThree = JAXBContext.newInstance(CollectionPeopleImpl.class);
                    Unmarshaller unmarshaller2 = contextTeamThree.createUnmarshaller();
                    teamThreelist = (CollectionPeopleImpl) unmarshaller2.unmarshal(fileTeamThree);
                    teamThreeView.getItems().setAll(teamThreelist.getListPeople());

                }catch (JAXBException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private synchronized void addAll(int index){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    contextTemp = JAXBContext.newInstance(Person.class);
                    Unmarshaller unmarshaller = contextTemp.createUnmarshaller();
                    Person person = (Person) unmarshaller.unmarshal(file);
                    playerReady.getItems().add(person);
                    listView.getItems().get(index).setReady(true);

                    persons.getListPeople().add(person);
                    contextPlayerReady = JAXBContext.newInstance(CollectionPeopleImpl.class);
                    Marshaller marshaller = contextPlayerReady.createMarshaller();
                    marshaller.marshal(persons,fileReadyPlayer);

                }catch (JAXBException e){
                    e.printStackTrace();
                }
            }
        });

    }

    private synchronized void minusAll(int index){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    contextTemp = JAXBContext.newInstance(Person.class);
                    Unmarshaller unmarshaller = contextTemp.createUnmarshaller();
                    Person person = (Person) unmarshaller.unmarshal(file);
                    playerReady.getItems().remove(index);
                    for(Person p: listView.getItems()){
                        if (p.getName().equals(person.getName())){
                            p.setReady(false);
                        }
                    }

                    persons.getListPeople().remove(index);
                    contextPlayerReady = JAXBContext.newInstance(CollectionPeopleImpl.class);
                    Marshaller marshaller = contextPlayerReady.createMarshaller();
                    marshaller.marshal(persons,fileReadyPlayer);
                }catch (JAXBException e){
                    e.printStackTrace();
                }

            }
        });
    }


    public void exit(){
        Platform.exit();
        connection.disconnect();
    }




    public void reset(){

        teamOneView.getItems().clear();
        teamTwoView.getItems().clear();
        teamThreeView.getItems().clear();
        teamOnelist.getListPeople().clear();
        teamTwolist.getListPeople().clear();
        teamThreelist.getListPeople().clear();
        //listView.getItems().clear();
        //people.fillPeopleData();
        //listView.setItems(people.getListPeople());
        //high = people.high();
        //normal = people.normal();
        //low = people.low();
        // balanceButton.setDisable(false);


    }


    public void allP() {
        allPlayer = people.ready();
        Collections.shuffle(allPlayer);
        for (int i = 0; i < allPlayer.size(); ) {
            teamOneView.getItems().add(allPlayer.get(i));
            ++i;
            if (i < allPlayer.size()) {
                teamTwoView.getItems().add(allPlayer.get(i));
                ++i;
            }
            if (i < allPlayer.size() && allPlayer.size() > 11) {
                teamThreeView.getItems().add(allPlayer.get(i));
                ++i;
            }

        }
        if (!checked()) {
            try {
                reset();
                allP();
            } catch (StackOverflowError e) {
                System.out.println("StackOverFlow");
                reset();
            }

        }

    }


    public boolean checked(){
        int avrTeamOne = 0;
        int avrTeamTwo = 0;
        int avrTeamThree = 0;
        for(int i = 0; i < teamOneView.getItems().size();i++){
            avrTeamOne+=teamOneView.getItems().get(i).avr();
        }
        System.out.println("Team one: "+avrTeamOne);
//        teamOneAvr.setText(String.valueOf(avrTeamOne));
        for(int i = 0; i < teamTwoView.getItems().size();i++){
            avrTeamTwo+=teamTwoView.getItems().get(i).avr();

        }
        System.out.println("Team two: "+avrTeamTwo);
//        teamTwoAvr.setText(String.valueOf(avrTeamTwo));
        for(int i = 0; i < teamThreeView.getItems().size();i++){
            avrTeamThree+=teamThreeView.getItems().get(i).avr();

        }

        System.out.println("Team three: " + avrTeamThree);
//        teamThreeAvr.setText(String.valueOf(avrTeamThree));

        if(avrTeamOne -  avrTeamTwo > 6 || (allPlayer.size() > 11 && avrTeamOne -  avrTeamThree > 6)){
            return false;
        }

        else if(avrTeamTwo - avrTeamOne > 6 ||  (allPlayer.size() > 11 && avrTeamTwo - avrTeamThree > 6)){
            return false;
        }
        else if(avrTeamThree -  avrTeamOne > 6 || avrTeamThree - avrTeamTwo > 6){
            return false;
        }
        else {
            try {

                    teamOnelist.getListPeople().addAll(teamOneView.getItems());
                    contextTeamOne = JAXBContext.newInstance(CollectionPeopleImpl.class);
                    Marshaller marshaller = contextTeamOne.createMarshaller();
                    marshaller.marshal(teamOnelist, fileTeamOne);



                    teamTwolist.getListPeople().addAll(teamTwoView.getItems());
                    contextTeamTwo = JAXBContext.newInstance(CollectionPeopleImpl.class);
                    Marshaller marshaller1 = contextTeamTwo.createMarshaller();
                    marshaller1.marshal(teamTwolist, fileTeamTwo);



                    teamThreelist.getListPeople().addAll(teamThreeView.getItems());
                    contextTeamThree = JAXBContext.newInstance(CollectionPeopleImpl.class);
                    Marshaller marshaller2 = contextTeamThree.createMarshaller();
                    marshaller2.marshal(teamThreelist,fileTeamThree);

            }catch (JAXBException e){
                e.printStackTrace();
            }
            System.out.println("All balance");
            return true;

        }


    }


    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        try {
            contextPlayerReady = JAXBContext.newInstance(CollectionPeopleImpl.class);
            Unmarshaller unmarshaller = contextPlayerReady.createUnmarshaller();
            persons = (CollectionPeopleImpl) unmarshaller.unmarshal(fileReadyPlayer);
            playerReady.getItems().addAll(persons.getListPeople());

            contextTeamOne = JAXBContext.newInstance(CollectionPeopleImpl.class);
            Unmarshaller unmarshaller1 = contextTeamOne.createUnmarshaller();
            teamOnelist = (CollectionPeopleImpl) unmarshaller1.unmarshal(fileTeamOne);
            teamOneView.getItems().setAll(teamOnelist.getListPeople());

            contextTeamTwo = JAXBContext.newInstance(CollectionPeopleImpl.class);
            Unmarshaller unmarshaller2 = contextTeamTwo.createUnmarshaller();
            teamTwolist = (CollectionPeopleImpl) unmarshaller2.unmarshal(fileTeamTwo);
            teamTwoView.getItems().setAll(teamTwolist.getListPeople());

            contextTeamThree = JAXBContext.newInstance(CollectionPeopleImpl.class);
            Unmarshaller unmarshaller3 = contextTeamThree.createUnmarshaller();
            teamThreelist = (CollectionPeopleImpl) unmarshaller3.unmarshal(fileTeamThree);
            teamThreeView.getItems().setAll(teamThreelist.getListPeople());


        }catch (JAXBException e){
            e.printStackTrace();
        }
        printMsg("Connection ready...");
        printMsg("Команды чата:\n!b - сбалансировать команды\n!c - очистить команды\n!n - оповещение всем");
    }

    @Override
    public void onRecieveString(TCPConnection tcpConnection, String value) {
        printMsg(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMsg("Connection close ");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection exception: " +e);
    }



}
