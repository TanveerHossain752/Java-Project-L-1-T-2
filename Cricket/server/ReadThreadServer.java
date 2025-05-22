package server;

import data.network.*;
import data.dataBase.Player;
import util.LoginInfo;
import util.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadThreadServer implements Runnable {
    private final Thread thread;
    private final NetworkUtil networkUtil;
    public HashMap<String, String> userMap;
    public List<NetworkUtil> clientList;

    public ReadThreadServer(HashMap<String, String> map, NetworkUtil networkUtil, List<NetworkUtil> cList) {
        this.userMap = map;
        this.networkUtil = networkUtil;
        this.clientList = cList;
        this.thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o != null) {
                    if (o instanceof LoginInfo) {
                        LoginInfo loginInfo = (LoginInfo) o;
                        String password = userMap.get(loginInfo.getUserName());
                        loginInfo.setStatus(loginInfo.getPassword().equals(password));
                        networkUtil.write(loginInfo);
                    }

                    if (o instanceof GetPlayer) {
                        new Thread(() -> {
                            String s = ((GetPlayer) o).getName();
                            List<Player> ClubPlayerList = new ArrayList<>();
                            List<Player> ClubMarketList = new ArrayList<>();
                            for (Player p : Server.getPlayerList()) {
                                // System.out.println(p);
                                if (p.getClub().equalsIgnoreCase(s)) {
                                    ClubPlayerList.add(p);
                                }
                            }

                            for (Player p : Server.getMarketList()) {
                                if (!(p.getClub().equalsIgnoreCase(s))) {
                                    ClubMarketList.add(p);
                                }
                            }

                            GetPlayerResponse getPlayerList = new GetPlayerResponse();
                            getPlayerList.setPlayerList(ClubPlayerList);
                            getPlayerList.setMarketList(ClubMarketList);

                            try {
                                networkUtil.write(getPlayerList);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }

                    if (o instanceof SellPlayer) {
                        new Thread(() -> {
                            Player p = ((SellPlayer) o).getPlayer();
                            List<Player> ClubPlayerList = ((SellPlayer) o).getPlayerList();
                            // String club = ((SellPlayer) o).getName();
                            List<Player> playerList = Server.getPlayerList();
                            for (Player pl : playerList) {
                                if (pl.getName().equalsIgnoreCase(p.getName())) {
                                    playerList.remove(pl);
                                    break;
                                }
                            }
                            Server.setPlayerList(playerList);
                            List<Player> MarketList = Server.getMarketList();
                            MarketList.add(p);
                            Server.setMarketList(MarketList);

                            SellPlayerResponse getSellList = new SellPlayerResponse();
                            getSellList.setPlayerList(ClubPlayerList);
                            getSellList.setMarketList(MarketList);

                            try {
                                for (NetworkUtil n : clientList) {
                                    n.write(getSellList);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }

                    if (o instanceof BuyPlayer) {
                        new Thread(() -> {
                            Player p = ((BuyPlayer) o).getPlayer();
                            List<Player> ClubPlayerList = ((BuyPlayer) o).getPlayerList();
                            List<Player> playerList = Server.getPlayerList();
                            for (Player pl : playerList) {
                                if (pl.getName().equalsIgnoreCase(p.getName())) {
                                    playerList.remove(pl);
                                    break;
                                }
                            }
                            playerList.add(p);
                            Server.setPlayerList(playerList);
                            // String club=((SellPlayer) o).getName();
                            List<Player> MarketList = Server.getMarketList();
                            for (Player pl : MarketList) {
                                if (pl.getName().equalsIgnoreCase(p.getName())) {
                                    MarketList.remove(pl);
                                    break;
                                }
                            }
                            Server.setMarketList(MarketList);

                            BuyPlayerResponse getBuyList = new BuyPlayerResponse();
                            getBuyList.setPlayerList(ClubPlayerList);
                            getBuyList.setMarketList(MarketList);

                            try {
                                for (NetworkUtil n : clientList) {
                                    n.write(getBuyList);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}