package sample.connections.networkpack;







import sample.Person;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class TCPConnection {

    private final Socket socket;
    private final Thread rxThread;
//    private final BufferedReader in;
//    private final BufferedWriter out;
    private final TCPConnectionListener eventListener;
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private final FileOutputStream file = new FileOutputStream("D:\\txt1.txt");
    private final FileInputStream fileInputStream = new FileInputStream("D:\\txt1.txt");






    public TCPConnection(TCPConnectionListener eventListener, Socket socket) throws IOException {
        this.eventListener = eventListener;
        this.socket = socket;
//        in = new BufferedReader(new InputStreamReader( socket.getInputStream(), Charset.forName("UTF-8")));
//        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),Charset.forName("UTF-8")));
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventListener.onConnectionReady(TCPConnection.this);
                    while(!rxThread.isInterrupted()){
                            eventListener.onRecieveString(TCPConnection.this, ois.readUTF());


                    }
                }catch (IOException  e){
                        eventListener.onException(TCPConnection.this,e);
                }finally {
                    eventListener.onDisconnect(TCPConnection.this);
                }
            }
        });
        rxThread.start();
    }


    public TCPConnection(TCPConnectionListener eventListener,String ipAddr, int port) throws IOException {
        this(eventListener,new Socket(ipAddr,port));

    }

    public Socket getSocket() {
        return socket;
    }

    public synchronized void sendString(String value){
        try {

            oos.writeUTF(value);
            oos.flush();

        } catch (IOException e) {
            eventListener.onException(TCPConnection.this,e);
            disconnect();
       }

    }






    public synchronized void disconnect(){
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this,e);
        }
    }

    @Override
    public String toString() {
        return "TCPConnection: " + socket.getInetAddress()+ ": "+ socket.getPort();
    }


}
