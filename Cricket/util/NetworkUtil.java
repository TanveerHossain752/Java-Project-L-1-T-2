package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkUtil {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public NetworkUtil(String address, int port) {
        try {
            socket = new Socket(address, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.out.println("In NetworkUtil : " + e.toString());
        }
    }

    public NetworkUtil(Socket address) {
        try {
            socket = address;
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.out.println("In NetworkUtil : " + e.toString());
        }
    }

    public Object read() {
        Object o = null;
        try {
            o = ois.readObject();
        } catch (Exception e) {
            System.out.println("Reading Error in network : " + e.toString());
        }
        return o;
    }

    public boolean write(Object o) {
        try {
            oos.writeObject(o);
            oos.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Writing  Error in network : " + e.toString());
        }
        return false;
    }

    public void closeConnection() {
        try {
            ois.close();
            oos.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Closing Error in network : " + e.toString());
        }
    }
}
