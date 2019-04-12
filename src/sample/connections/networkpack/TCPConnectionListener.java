package sample.connections.networkpack;


import javafx.scene.control.ListView;
import sample.Person;

public interface TCPConnectionListener {

    void onConnectionReady(TCPConnection tcpConnection);
    void onRecieveString(TCPConnection tcpConnection, String value);
    void onDisconnect(TCPConnection tcpConnection);
    void onException(TCPConnection tcpConnection, Exception e);







}
