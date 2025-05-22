package server;

import data.dataBase.FileOperations;
import data.dataBase.Player;
import util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    public HashMap<String, String> clientMap;

    public static List<Player> playerList;
    public static List<Player> marketList;
    public static List<NetworkUtil> clientList;

    public static void setPlayerList(List<Player> playerList) {
        Server.playerList = playerList;
    }

    public static void setMarketList(List<Player> marketList) {
        Server.marketList = marketList;
    }

    Server() throws IOException {
        playerList = new ArrayList<>();
        marketList = new ArrayList<>();
        clientList = new ArrayList<>();
        playerList = FileOperations.readFromFile();
        clientMap = new HashMap<>();
        for (Player player : playerList) {
            boolean isMarket = false;
            for (String s : clientMap.keySet()) {
                if (s.equals(player.getName())) {
                    isMarket = true;
                    break;
                }
            }
            if (!isMarket) {
                clientMap.put(player.getClub(), "123");
            }
        }
        try {
            serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        clientList.add(networkUtil);
        new ReadThreadServer(clientMap, networkUtil, clientList);
    }

    public static List<Player> getPlayerList() {
        return playerList;
    }

    public static List<Player> getMarketList() {
        return marketList;
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
