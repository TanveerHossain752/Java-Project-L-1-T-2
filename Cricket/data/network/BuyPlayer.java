package data.network;

import java.io.Serializable;
import java.util.List;

import data.dataBase.Player;

public class BuyPlayer implements Serializable {
    Player player;
    private List<Player> playerList;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

}
