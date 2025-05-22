package client;

import data.network.BuyPlayer;
import data.network.SellPlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import data.dataBase.FrequentMethods;
import data.dataBase.Player;
import data.dataBase.Search;

import java.util.ArrayList;
import java.util.List;

public class ClubHome {
    private Client client;
    private String club;

    public static List<Player> playerList = new ArrayList<>();
    public static List<Player> marketList = new ArrayList<>();

    @FXML
    private Label message;

    @FXML
    private Button logOutButton;

    @FXML
    private TableView<Player> playerTable;

    @FXML
    private TableColumn<Player, String> playerNameColumn;

    @FXML
    private TableColumn<Player, String> playerCountryColumn;

    @FXML
    private TableColumn<Player, Integer> playerAgeColumn;

    @FXML
    private TableColumn<Player, Double> playerHeightColumn;

    @FXML
    private TableColumn<Player, String> playerClubColumn;

    @FXML
    private TableColumn<Player, String> playerPositionColumn;

    @FXML
    private TableColumn<Player, Integer> playerNumberColumn;

    @FXML
    private TableColumn<Player, Integer> playerSalaryColumn;

    @FXML
    private Text introText;

    @FXML
    private Text introText1;

    @FXML
    private TextField newField;

    @FXML
    private TextField newField2;

    @FXML
    private Button confirmButton;

    @FXML
    private Button backButton;

    public void init(String msg) {
        club = msg;
        message.setText("Welcome to " + club + " Club");
        FrequentMethods.hover(logOutButton);
        FrequentMethods.hover(nameSearch);
        FrequentMethods.hover(confirmButton);
        FrequentMethods.hover(backButton);
        FrequentMethods.hover(countrySearch);
        FrequentMethods.hover(positionSearch);
        FrequentMethods.hover(salarySearch);
        FrequentMethods.hover(countryPlayerCount);
        FrequentMethods.hover(maxSalary);
        FrequentMethods.hover(maxAge);
        FrequentMethods.hover(maxHeight);
        FrequentMethods.hover(totalSalary);
        FrequentMethods.hover(SellPlayer);
        FrequentMethods.hover(PlayerMarket);
        FrequentMethods.hover(BuyPlayer);
        FrequentMethods.hover(Refresh);
        FrequentMethods.hover(PlayerInfo);
        introText.setVisible(false);
        introText1.setVisible(false);
        newField.setVisible(false);
        newField2.setVisible(false);
        confirmButton.setVisible(false);
        backButton.setVisible(false);
        Refresh.setVisible(false);
        BuyPlayer.setVisible(false);
        playerList = Client.getPlayerList();
    }

    @FXML
    void logOut(ActionEvent event) {
        try {
            client.showLogin();
            playerList.clear();
            Client.setPlayerList(playerList);
            marketList.clear();
            Client.setMarketList(marketList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Back(ActionEvent event) {
        try {
            client.showHome(club);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button PlayerInfo;

    @FXML
    void playerInfo(ActionEvent event) {
        Player player = playerTable.getSelectionModel().getSelectedItem();
        if (player == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Player Selected");
            alert.setContentText("Please select a player to view info");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Player Info");
            alert.setHeaderText("Player Information");
            alert.setContentText(player.toString());
            alert.showAndWait();
        }
    }

    @FXML
    private Button nameSearch;

    @FXML
    void NameSearch(ActionEvent event) {
        introText.setText("Enter the name of the player you want to search");
        visible();
        confirmButton.setOnAction(e -> {
            List<Player> searchList = new ArrayList<>();
            searchList = Search.SearchPlayerName(newField.getText(), playerList);
            if (searchList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No Player Found");
                alert.setContentText("Please enter a valid name");
                alert.showAndWait();
            } else {
                data = FXCollections.observableArrayList(searchList);

                playerTable.setEditable(true);
                playerTable.setItems(data);
            }
        });

    }

    @FXML
    private Button countrySearch;

    @FXML
    void CountrySearch(ActionEvent event) {
        introText.setText("Enter the country of the player you want to search");
        visible();
        confirmButton.setOnAction(e -> {
            List<Player> searchList = new ArrayList<>();
            searchList = Search.SearchCountryName(newField.getText(), playerList);
            if (searchList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No Player Found");
                alert.setContentText("Please enter a valid country");
                alert.showAndWait();
            } else {
                data = FXCollections.observableArrayList(searchList);

                playerTable.setEditable(true);
                playerTable.setItems(data);
            }
        });
    }

    @FXML
    private Button positionSearch;

    @FXML
    void PositionSearch(ActionEvent event) {
        introText.setText("Enter the position of the player you want to search");
        visible();
        confirmButton.setOnAction(e -> {
            List<Player> searchList = new ArrayList<>();
            searchList = Search.SearchPosition(newField.getText(), playerList);
            if (searchList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No Player Found");
                alert.setContentText("Please enter a valid position");
                alert.showAndWait();
            } else {
                data = FXCollections.observableArrayList(searchList);

                playerTable.setEditable(true);
                playerTable.setItems(data);
            }
        });
    }

    @FXML
    private Button salarySearch;

    @FXML
    void SalarySearch(ActionEvent event) {
        introText.setText("Enter Minimum Salary");
        introText1.setText("Enter Maximum Salary");
        visible2();
        confirmButton.setOnAction(e -> {
            List<Player> searchList = new ArrayList<>();
            try {
                int min = Integer.parseInt(newField.getText());
                int max = Integer.parseInt(newField2.getText());
                searchList = Search.SearchSalary(min, max, playerList);
                if (searchList.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("No Player Found");
                    alert.setContentText("Please enter a valid salary range");
                    alert.showAndWait();
                } else {
                    data = FXCollections.observableArrayList(searchList);

                    playerTable.setEditable(true);
                    playerTable.setItems(data);
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Salary");
                alert.setContentText("Please enter a valid salary range");
                alert.showAndWait();
            }
        });
    }

    @FXML
    private Button countryPlayerCount;

    @FXML
    void CountryPlayerCount(ActionEvent event) {
        try {
            client.shoWClubPlayerCount(club, playerList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button maxSalary;

    @FXML
    void MaxSalary(ActionEvent event) {
        backButton.setVisible(true);
        List<Player> searchList = new ArrayList<>();
        searchList = Search.MaxSalary(playerList);

        data = FXCollections.observableArrayList(searchList);

        playerTable.setEditable(true);
        playerTable.setItems(data);
    }

    @FXML
    private Button maxAge;

    @FXML
    void MaxAge(ActionEvent event) {
        backButton.setVisible(true);
        List<Player> searchList = new ArrayList<>();
        searchList = Search.MaxAge(playerList);

        data = FXCollections.observableArrayList(searchList);

        playerTable.setEditable(true);
        playerTable.setItems(data);
    }

    @FXML
    private Button maxHeight;

    @FXML
    void MaxHeight(ActionEvent event) {
        backButton.setVisible(true);
        List<Player> searchList = new ArrayList<>();
        searchList = Search.MaxHeight(playerList);

        data = FXCollections.observableArrayList(searchList);

        playerTable.setEditable(true);
        playerTable.setItems(data);
    }

    @FXML
    private Button totalSalary;

    @FXML
    void TotalSalary(ActionEvent event) {
        backButton.setVisible(true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Total Salary");
        alert.setHeaderText("Total Salary of the Club");
        alert.setContentText("Total Salary: " + Search.TotalSalary(playerList));
        alert.showAndWait();
    }

    @FXML
    private Button SellPlayer;

    @FXML
    void sellPlayer(ActionEvent event) {
        Player player = playerTable.getSelectionModel().getSelectedItem();
        if (player == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Player Selected");
            alert.setContentText("Please select a player to sell");
            alert.showAndWait();
        }
        new Thread(() -> {
            try {
                playerList.remove(player);
                SellPlayer sellPlayer = new SellPlayer();
                sellPlayer.setPlayer(player);
                sellPlayer.setName(club);
                sellPlayer.setPlayerList(playerList);
                client.getNetworkUtil().write(sellPlayer);
            } catch (Exception e) {
                e.printStackTrace();
            }

            data = FXCollections.observableArrayList(playerList);

            playerTable.setEditable(true);
            playerTable.setItems(data);
        }).start();
    }

    @FXML
    private Button PlayerMarket;

    @FXML
    void playerMarket(ActionEvent event) {
        backButton.setVisible(true);
        Refresh.setVisible(true);
        BuyPlayer.setVisible(true);
        PlayerMarket.setVisible(false);
        SellPlayer.setVisible(false);

        marketList = Client.getMarketList();

        data = FXCollections.observableArrayList(marketList);

        playerTable.setEditable(true);
        playerTable.setItems(data);
    }

    @FXML
    private Button BuyPlayer;

    @FXML
    void buyPlayer(ActionEvent event) {
        Player player = playerTable.getSelectionModel().getSelectedItem();
        if (player == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Player Selected");
            alert.setContentText("Please select a player to buy");
            alert.showAndWait();
        } else {
            boolean b = false;
            for (Player p : Client.getMarketList()) {
                if (p.getName().equals(player.getName())) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Player not in Market");
                alert.setContentText("Please select a player from the market");
                alert.showAndWait();
            } else {
                new Thread(() -> {
                    try {
                        player.setClub(club);
                        marketList.remove(player);
                        playerList.add(player);
                        BuyPlayer buyPlayer = new BuyPlayer();
                        buyPlayer.setPlayer(player);
                        buyPlayer.setName(club);
                        buyPlayer.setPlayerList(playerList);
                        client.getNetworkUtil().write(buyPlayer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    data = FXCollections.observableArrayList(marketList);

                    playerTable.setEditable(true);
                    playerTable.setItems(data);
                }).start();
            }
        }
    }

    @FXML
    private Button Refresh;

    @FXML
    void refresh() {
        new Thread(() -> {
            marketList = Client.getMarketList();
            data = FXCollections.observableArrayList(marketList);

            playerTable.setEditable(true);
            playerTable.setItems(data);
        }).start();
    }

    void setClient(Client client) {
        this.client = client;
    }

    ObservableList<Player> data;

    private boolean init = true;

    private void initializeColumns() {
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));

        playerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("Country"));

        playerAgeColumn.setCellValueFactory(new PropertyValueFactory<>("Age_in_years"));

        playerHeightColumn.setCellValueFactory(new PropertyValueFactory<>("Height_in_meters"));

        playerClubColumn.setCellValueFactory(new PropertyValueFactory<>("Club"));

        playerPositionColumn.setCellValueFactory(new PropertyValueFactory<>("Position"));

        playerNumberColumn.setCellValueFactory(new PropertyValueFactory<>("Number"));

        playerSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("Weekly_Salary"));

        // buttonCol.setCellValueFactory(new PropertyValueFactory<>("button"));
    }

    public void load() {
        if (init) {
            initializeColumns();
            init = false;
        }

        data = FXCollections.observableArrayList(playerList);

        playerTable.setEditable(true);
        playerTable.setItems(data);
    }

    private void visible() {
        introText.setVisible(true);
        newField.setVisible(true);
        confirmButton.setVisible(true);
        backButton.setVisible(true);
    }

    private void visible2() {
        visible();
        introText1.setVisible(true);
        newField2.setVisible(true);
    }
}