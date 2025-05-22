package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import data.dataBase.Country;
import data.dataBase.FrequentMethods;
import data.dataBase.Player;

import java.util.ArrayList;
import java.util.List;

public class ClubPlayerCount {
    private static List<Country> countryList = new ArrayList<>();
    private String clubName;
    private Client client;

    @FXML
    private TableView<Country> playerTable;

    @FXML
    private TableColumn<Country, String> nameColumn;

    @FXML
    private TableColumn<Country, Integer> countColumn;

    @FXML
    private Button backButton;

    public void init(String clubname, List<Player> players) {
        this.clubName = clubname;
        FrequentMethods.hover(backButton);
        for (Player player : players) {
            boolean found = false;
            for (Country country : countryList) {
                if (player.getCountry().equals(country.getName())) {
                    found = true;
                    country.setPlayerCount(country.getPlayerCount() + 1);
                }
            }
            if (!found) {
                Country country = new Country(player.getCountry(), 1);
                countryList.add(country);
            }
        }
    }

    @FXML
    void back(ActionEvent event) {
        try {
            client.showHome(clubName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setClient(Client client) {
        this.client = client;
    }

    ObservableList<Country> data;

    private boolean init = true;

    public void initializeColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("playerCount"));
    }

    public void load() {
        if (init) {
            initializeColumns();
            init = false;
        }
        data = FXCollections.observableArrayList(countryList);

        playerTable.setEditable(true);
        playerTable.setItems(data);
    }
}
