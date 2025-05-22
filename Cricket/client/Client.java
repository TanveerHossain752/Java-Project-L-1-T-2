package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import data.dataBase.Player;
import util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class Client extends Application{
    private Stage stage;
    private NetworkUtil networkUtil;
    public static List<Player> playerList = new ArrayList<>();
    public static List<Player> marketList = new ArrayList<>();

    public Stage getStage() {
        return stage;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    public static List<Player> getPlayerList() {
        return playerList;
    }   

    public static void setPlayerList(List<Player> playerList) {
        Client.playerList = playerList;
    }

    public static List<Player> getMarketList() {
        return marketList;
    }

    public static void setMarketList(List<Player> marketList) {
        Client.marketList = marketList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        connectToServer();
        showLogin();
    }

    private void connectToServer() {
        String serverAddress = "127.0.1.3";
        int serverPort = 33333;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        new ReadThreadClient(this);
    }

    public void showLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/LoginScreen.fxml"));
        Parent root = loader.load();

        ClubLogin controller = loader.getController();
        controller.init();
        controller.setClient(this);

        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showHome(String username) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/HomeScreen.fxml"));
        Parent root = loader.load();

        ClubHome controller = loader.getController();
        controller.init(username);
        controller.load();
        controller.setClient(this);

        stage.setTitle("Home");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void shoWClubPlayerCount(String club,List<Player> playerlList) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ClubPlayerCount.fxml"));
        Parent root = loader.load();

        ClubPlayerCount controller = loader.getController();
        controller.init(club,playerlList);
        controller.setClient(this);

        stage.setTitle("Club Player Count");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Login not supported");
        alert.setContentText("Username or password is incorrect");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}