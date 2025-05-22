package data.dataBase;

import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Club implements Serializable {
    private String name;
    private int budget;
    private List<Player> playerList;
    
    private final int MAX_PLAYER_COUNT = 11;

    public Club(){
        playerList = new ArrayList<>();
    }
    public Club(Player player){
        playerList = new ArrayList<>();
        setName(player.getClub());
        addPlayer(player);
    }

    public String getName() {
        return name;
    }

    public int getBudget() {
        return budget;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public int playerCount(){
        return playerList.size();
    }

    public int getMAX_PLAYER_COUNT() {
        return MAX_PLAYER_COUNT;
    }

    public void addPlayer(Player player){
        playerList.add(player);
    }

    public void removePlayer(String playerName){
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getName().equalsIgnoreCase(playerName)) {
                playerList.remove(i);
                return;
            }
        }
    }

    public boolean checkNumber(int number) {
        for (Player player : playerList) {
            if (player.getNumber() == number) return true;
        }
        return false;
    }
     public List<String> getCountryList() {
        Set<String> countrySet = new LinkedHashSet<>();
        this.playerList.forEach(e -> countrySet.add(e.getCountry()));
        return new ArrayList<>(countrySet);
    }

    public List<String> getPositionList() {
        Set<String> positionSet = new LinkedHashSet<>();
        this.playerList.forEach(e -> positionSet.add(e.getPosition()));
        return new ArrayList<>(positionSet);
    }

    public List<Player> getMaxSalaryPlayers() {
        List<Player> playerList = new ArrayList<>();

        // find maximum value of salary
        int salary = playerList.get(0).getWeekly_Salary();
        for (int i = 1; i < playerList.size(); i++) {
            if (playerList.get(i).getWeekly_Salary() > salary) {
                salary = playerList.get(i).getWeekly_Salary();
            }
        }

        // find players with maximum salary
        int eps = 0; // precision range
        for (int i = 0; i < this.playerList.size(); i++) {
            Player player = this.playerList.get(i);
            if (Math.abs(salary - player.getWeekly_Salary()) < eps) {
                playerList.add(player);
            }
        }
        return playerList;
    }

    public List<Player> getMaxAgePlayers() {
        List<Player> playerList = new ArrayList<>();

        // find maximum age
        int age = playerList.get(0).getAge_in_years();
        for (int i = 1; i < playerList.size(); i++) {
            if (playerList.get(i).getAge_in_years() > age) {
                age = playerList.get(i).getAge_in_years();
            }
        }

        // find players with maximum age
        for (int i = 0; i < this.playerList.size(); i++) {
            Player player = this.playerList.get(i);
            if (player.getAge_in_years() == age) {
                playerList.add(player);
            }
        }
        return playerList;
    }

    public List<Player> getMaxHeightPlayers() {
        List<Player> playerList = new ArrayList<>();

        // find maximum height
        double height = playerList.get(0).getHeight_in_meters();
        for (int i = 1; i < playerList.size(); i++) {
            if (playerList.get(i).getHeight_in_meters() > height) {
                height = playerList.get(i).getHeight_in_meters();
            }
        }

        // find players with maximum height
        double eps = 0.000001;
        for (int i = 0; i < this.playerList.size(); i++) {
            Player player = this.playerList.get(i);
            if (Math.abs(height - player.getHeight_in_meters()) < eps) {
                playerList.add(player);
            }
        }
        return playerList;
    }

    public int getTotalYearlySalary() {
        int sum = 0;
        for (Player player : playerList) {
            sum += player.getWeekly_Salary();
        }
        sum *= 52; // 52 weeks in a year
        return sum;
    }
}