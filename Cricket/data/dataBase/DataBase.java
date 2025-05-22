package data.dataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {
    private List<Player> PlayerList;
    private List<Club> ClubList;
    private List<Country> CountryList;

    public DataBase() {
        PlayerList = new ArrayList<>();
        ClubList = new ArrayList<>();
        CountryList = new ArrayList<>();
    }

    public List<Player> getPlayerList() {
        return PlayerList;
    }

    public void setPlayerList(List<Player> playerList) {
        PlayerList = playerList;
    }

    public List<Club> getClubList() {
        return ClubList;
    }

    public void setClubList(List<Club> clubList) {
        ClubList = clubList;
    }

    public List<Country> getCountryList() {
        return CountryList;
    }

    public void setCountryList(List<Country> countryList) {
        CountryList = countryList;
    }

    public void addPlayer(Player player) {
        PlayerList.add(player);
    }

    public void addPlayer(List<Player> playerList) {
        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);
            addPlayer(player);
        }
    }

    public List<Player> searchByPlayerName(String Name) {
        List<Player> playerList = new ArrayList<>();
        for (Player player : PlayerList) {
            if (player.getName().equalsIgnoreCase(Name)) {
                PlayerList.add(player);
            }
        }
        return playerList;
    }

    public List<Player> searchByClubAndCountry(String CountryName, String ClubName) {
        List<Player> playerList = new ArrayList<>();
        for (Player player : PlayerList) {
            if (player.getCountry().equalsIgnoreCase(CountryName)
                    && (ClubName.equalsIgnoreCase("ANY") || player.getClub().equalsIgnoreCase(ClubName))) {
                playerList.add(player);
            }
        }
        return playerList;
    }

    public List<Player> searchByPosition(String Position) {
        List<Player> playerList = new ArrayList<>();
        for (Player player : PlayerList) {
            if (player.getPosition().equalsIgnoreCase(Position)) {
                playerList.add(player);
            }
        }
        return playerList;
    }

    public List<Player> searchBySalaryRange(double lo, double hi) {
        List<Player> playerList = new ArrayList<>();
        for (Player player : PlayerList) {
            if (player.getWeekly_Salary() >= lo && player.getWeekly_Salary() <= hi) {
                playerList.add(player);
            }
        }
        return playerList;
    }

    public Map<String, Integer> CountryWisePlayerCount() {
        Map<String, Integer> countries = new HashMap<>();

        // Count players for each country
        for (Player player : PlayerList) {
            String country = player.getCountry().trim().toLowerCase();
            countries.put(country, countries.getOrDefault(country, 0) + 1);
        }

        // Create a new map with capitalized country names
        Map<String, Integer> formattedCountries = new HashMap<>();
        for (Map.Entry<String, Integer> entry : countries.entrySet()) {
            String country = entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
            formattedCountries.put(country, entry.getValue());
        }

        return formattedCountries;
    }

    public List<Player> MaximumSalaryOfAClub(String ClubName) {
        boolean foundMatch = false;
        List<Player> ClubPlayerList = new ArrayList<>();
        for (Player player : PlayerList) {
            if (player.getClub().equalsIgnoreCase(ClubName)) {
                ClubPlayerList.add(player);
                foundMatch = true;
            }
        }
        if (!foundMatch) {
            System.out.println("No such club with this name");
            return null;
        } else {
            List<Player> maxSalaryPlayer = new ArrayList<>();
            int maxSalary = ClubPlayerList.getFirst().getWeekly_Salary();
            for (Player player : ClubPlayerList) {
                if (player.getWeekly_Salary() > maxSalary) {
                    maxSalary = player.getWeekly_Salary();
                }
            }
            for (Player player : ClubPlayerList) {
                if (player.getWeekly_Salary() == maxSalary) {
                    maxSalaryPlayer.add(player);
                }
            }
            return maxSalaryPlayer;
        }

    }

    public List<Player> MaximumAgeOfAClub(String ClubName) {
        boolean foundMatch = false;
        List<Player> ClubPlayerList = new ArrayList<>();
        for (Player player : PlayerList) {
            if (player.getClub().equalsIgnoreCase(ClubName)) {
                ClubPlayerList.add(player);
                foundMatch = true;
            }
        }
        if (!foundMatch) {
            System.out.println("No such club with this name");
            return null;
        } else {
            List<Player> maxAgePlayer = new ArrayList<>();
            double maxAge = ClubPlayerList.getFirst().getAge_in_years();
            for (Player player : ClubPlayerList) {
                if (player.getAge_in_years() > maxAge) {
                    maxAge = player.getAge_in_years();
                }
            }
            for (Player player : ClubPlayerList) {
                if (player.getAge_in_years() == maxAge) {
                    maxAgePlayer.add(player);
                }
            }
            return maxAgePlayer;
        }
    }

    public List<Player> MaximumHeightOfAClub(String ClubName) {
        boolean foundMatch = false;
        List<Player> CLubPlayerList = new ArrayList<>();
        for (Player player : PlayerList) {
            if (player.getClub().equalsIgnoreCase(ClubName)) {
                CLubPlayerList.add(player);
                foundMatch = true;
            }
        }
        if (!foundMatch) {
            System.out.println("No such club with this name");
            return null;
        } else {
            List<Player> maxHeightPlayer = new ArrayList<>();
            double maxHeight = CLubPlayerList.getFirst().getHeight_in_meters();
            for (Player player : CLubPlayerList) {
                if (player.getHeight_in_meters() > maxHeight) {
                    maxHeight = player.getHeight_in_meters();
                }
            }
            for (Player player : CLubPlayerList) {
                if (player.getHeight_in_meters() == maxHeight) {
                    maxHeightPlayer.add(player);
                }
            }
            return maxHeightPlayer;
        }
    }

    public List<Player> TotalSalaryOfAClub(String ClubName) {
        boolean foundMatch = false;
        List<Player> ClubPlayerList = new ArrayList<>();
        for (Player player : PlayerList) {
            if (player.getClub().equalsIgnoreCase(ClubName)) {
                ClubPlayerList.add(player);
                foundMatch = true;
            }
        }
        if (!foundMatch) {
            System.out.println("No such club with this name");
            return null;
        } else {
            return ClubPlayerList;
        }
    }
}
