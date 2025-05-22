package client;

import data.network.BuyPlayerResponse;
import data.network.GetPlayerResponse;
import data.network.SellPlayerResponse;
import javafx.application.Platform;
import data.dataBase.Player;
import util.LoginInfo;

import java.util.ArrayList;
import java.util.List;

public class ReadThreadClient implements Runnable {
    private final Thread thread;
    private final Client client;
    public static List<Player> playerList = new ArrayList<>();
    public static List<Player> marketList = new ArrayList<>();

    public ReadThreadClient(Client client) {
        this.client = client;
        this.thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = client.getNetworkUtil().read();
                if (o != null) {
                    if (o instanceof LoginInfo) {
                        LoginInfo loginInfo = (LoginInfo) o;
                        System.out.println(loginInfo.getUserName());
                        System.out.println(loginInfo.isStatus());
                        // the following is necessary to update JavaFX UI components from user created
                        // threads
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (loginInfo.isStatus()) {
                                    try {
                                        client.showHome(loginInfo.getUserName());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    client.showAlert();
                                }
                            }
                        });
                    }
                    if (o instanceof GetPlayerResponse) {
                        new Thread(() -> {
                            GetPlayerResponse obj = (GetPlayerResponse) o;
                            playerList = obj.getPlayerList();
                            marketList = obj.getMarketList();
                            Client.setPlayerList(playerList);
                            Client.setMarketList(marketList);
                        }).start();
                    }

                    if (o instanceof SellPlayerResponse) {
                        new Thread(() -> {
                            SellPlayerResponse obj = (SellPlayerResponse) o;
                            playerList = obj.getPlayerList();
                            marketList = obj.getMarketList();
                            String s = "";
                            String c = "";
                            for (Player p : playerList) {
                                s = p.getClub();
                            }
                            for (Player p : Client.getPlayerList()) {
                                c = p.getClub();
                            }
                            List<Player> ClubMarketList = new ArrayList<>();
                            for (Player p : marketList) {
                                if (!(p.getClub().equalsIgnoreCase(c))) {
                                    ClubMarketList.add(p);
                                }
                            }
                            if (s.equalsIgnoreCase(c))
                                Client.setPlayerList(playerList);
                            Client.setMarketList(ClubMarketList);
                        }).start();
                    }

                    if (o instanceof BuyPlayerResponse) {
                        new Thread(() -> {
                            BuyPlayerResponse obj = (BuyPlayerResponse) o;
                            playerList = obj.getPlayerList();
                            marketList = obj.getMarketList();
                            String s = "";
                            String c = "";
                            for (Player p : playerList) {
                                s = p.getClub();
                            }
                            for (Player p : Client.getPlayerList()) {
                                c = p.getClub();
                            }
                            List<Player> ClubMarketList = new ArrayList<>();
                            for (Player p : marketList) {
                                if (!(p.getClub().equalsIgnoreCase(c))) {
                                    ClubMarketList.add(p);
                                }
                            }
                            if (s.equalsIgnoreCase(c))
                                Client.setPlayerList(playerList);
                            Client.setMarketList(ClubMarketList);
                        }).start();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                client.getNetworkUtil().closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}