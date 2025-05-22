package client;

import data.network.GetPlayer;
import data.dataBase.FrequentMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import util.LoginInfo;
// import util.NetworkUtil;

public class ClubLogin {
    private Client client;
    // private NetworkUtil networkUtil;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button resetButton;

    @FXML
    public Line line;

    @FXML
    public Line line1;

    public void init() {
        FrequentMethods.hover(loginButton);
        FrequentMethods.hover(resetButton);
    }

    @FXML
    void login(ActionEvent event) {
        new Thread(() -> {
            String username = usernameField.getText();
            username = FrequentMethods.capitalizeWord(username);
            String password = passwordField.getText();
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUserName(username);
            loginInfo.setPassword(password);
            try {
                client.getNetworkUtil().write(loginInfo);
                GetPlayer getPlayer = new GetPlayer();
                getPlayer.setName(username);
                client.getNetworkUtil().write(getPlayer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void reset(ActionEvent event) {
        usernameField.setText(null);
        passwordField.setText(null);
    }

    void setClient(Client client) {
        this.client = client;
    }
}
