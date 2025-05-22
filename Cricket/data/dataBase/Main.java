package data.dataBase;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.*;
import java.util.*;

public class Main extends Application {
    private static final DataBase dataBase = new DataBase();
    private static final String FileName = "players.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load player data from the file
        loadPlayersFromFile();

        // Create the main menu
        primaryStage.setTitle("Cricket Player Database System");

        // Main menu scene
        BorderPane root = new BorderPane();
        Scene mainMenuScene = new Scene(root, 500, 400);

        Button searchPlayersButton = new Button("Search Players");
        Button searchClubsButton = new Button("Search Clubs");
        Button addPlayerButton = new Button("Add Player");
        Button exitButton = new Button("Exit System");

        searchPlayersButton.setOnAction(e -> showSearchPlayersMenu(primaryStage));
        searchClubsButton.setOnAction(e -> showSearchClubsMenu(primaryStage));
        addPlayerButton.setOnAction(e -> showAddPlayerMenu(primaryStage));
        exitButton.setOnAction(e -> saveAndExit(primaryStage));

        VBox mainMenuVBox = new VBox(10, searchPlayersButton, searchClubsButton, addPlayerButton, exitButton);
        root.setCenter(mainMenuVBox);

        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    private void showSearchPlayersMenu(Stage primaryStage) {
        // Search players options
        Stage searchStage = new Stage();
        searchStage.setTitle("Player Searching Options:");

        VBox searchVBox = new VBox(10);
        Label searchLabel = new Label("Choose Search Criteria:");

        Button byNameButton = new Button("By Player Name");
        Button byClubCountryButton = new Button("By Club and Country");
        Button byPositionButton = new Button("By Position");
        Button bySalaryRangeButton = new Button("By Salary Range");
        Button countryWiseCountButton = new Button("Country-wise Player Count");
        Button backButton = new Button("Back to Main Menu");

        // Set button actions
        byNameButton.setOnAction(e -> searchByName());
        byClubCountryButton.setOnAction(e -> searchByClubCountry());
        byPositionButton.setOnAction(e -> searchByPosition());
        bySalaryRangeButton.setOnAction(e -> searchBySalaryRange());
        countryWiseCountButton.setOnAction(e -> displayCountryWiseCount());
        backButton.setOnAction(e -> searchStage.close());

        searchVBox.getChildren().addAll(searchLabel, byNameButton, byClubCountryButton, byPositionButton,
                bySalaryRangeButton, countryWiseCountButton, backButton);
        Scene searchScene = new Scene(searchVBox, 400, 400);
        searchStage.setScene(searchScene);
        searchStage.show();
    }

    private void showAddPlayerMenu(Stage primaryStage) {
        // Add player stage
        Stage addPlayerStage = new Stage();
        addPlayerStage.setTitle("Add Player");

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Player");
        dialog.setHeaderText("Enter Player Name");
        dialog.setContentText("Enter Player Name");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(Name -> {
            List<Player> foundPlayers = dataBase.searchByPlayerName(Name);
            if (!foundPlayers.isEmpty()) {
                showAlert("Player with this name already exists.");
            } else {

                // Creating input fields and labels
                Label nameLabel = new Label("Name:");
                TextField nameField = new TextField(Name);

                Label countryLabel = new Label("Country:");
                TextField countryField = new TextField();

                Label ageLabel = new Label("Age:");
                TextField ageField = new TextField();

                Label heightLabel = new Label("Height (in meters):");
                TextField heightField = new TextField();

                Label clubLabel = new Label("Club:");
                TextField clubField = new TextField();

                Label positionLabel = new Label("Position:");
                TextField positionField = new TextField();

                Label numberLabel = new Label("Number:");
                TextField numberField = new TextField();

                Label salaryLabel = new Label("Salary:");
                TextField salaryField = new TextField();

                // New Label to show status
                Label statusLabel = new Label();
                statusLabel.setTextFill(Color.RED); // Set the text color to red for errors

                // Buttons
                Button addButton = new Button("Add Player");
                Button cancelButton = new Button("Cancel");

                // Layout setup
                GridPane gridPane = new GridPane();
                gridPane.setHgap(10);
                gridPane.setVgap(10);
                gridPane.setPadding(new Insets(10));

                gridPane.add(nameLabel, 0, 0);
                gridPane.add(nameField, 1, 0);
                gridPane.add(countryLabel, 0, 1);
                gridPane.add(countryField, 1, 1);
                gridPane.add(ageLabel, 0, 2);
                gridPane.add(ageField, 1, 2);
                gridPane.add(heightLabel, 0, 3);
                gridPane.add(heightField, 1, 3);
                gridPane.add(clubLabel, 0, 4);
                gridPane.add(clubField, 1, 4);
                gridPane.add(positionLabel, 0, 5);
                gridPane.add(positionField, 1, 5);
                gridPane.add(numberLabel, 0, 6);
                gridPane.add(numberField, 1, 6);
                gridPane.add(salaryLabel, 0, 7);
                gridPane.add(salaryField, 1, 7);

                // Adding Status Label
                gridPane.add(statusLabel, 1, 8);

                HBox buttonBox = new HBox(10, addButton, cancelButton);
                buttonBox.setAlignment(Pos.CENTER);
                gridPane.add(buttonBox, 0, 9, 2, 1);

                Scene scene = new Scene(gridPane, 400, 400);
                addPlayerStage.setScene(scene);
                addPlayerStage.initModality(Modality.WINDOW_MODAL);
                addPlayerStage.initOwner(primaryStage);
                addPlayerStage.show();

                // Action for "Add Player" button
                addButton.setOnAction(e -> {
                    try {
                        String name = nameField.getText().trim();
                        String country = countryField.getText().trim();
                        int age = Integer.parseInt(ageField.getText().trim());
                        double height = Double.parseDouble(heightField.getText().trim());
                        String club = clubField.getText().trim();
                        String position = positionField.getText().trim();
                        int number = Integer.parseInt(numberField.getText().trim());
                        int salary = Integer.parseInt(salaryField.getText().trim());

                        // Validate input fields
                        if (name.isEmpty() || country.isEmpty() || club.isEmpty() || position.isEmpty()) {
                            statusLabel.setText("Error: All text fields must be filled.");
                            return;
                        }
                        if (age <= 0 || height <= 0 || number <= 0 || salary <= 0) {
                            statusLabel.setText("Error: Age, height, number, and salary must be positive values.");
                            return;
                        }

                        // Check if player already exists
                        boolean playerExists = dataBase.getPlayerList().stream()
                                .anyMatch(player -> player.getName().equalsIgnoreCase(name)
                                        || player.getNumber() == number);
                        if (playerExists) {
                            statusLabel.setText("Error: A player with the same name or number already exists.");
                            return;
                        }

                        // Add player to the database
                        Player newPlayer = new Player(name, country, age, height, club, position, number, salary);
                        dataBase.getPlayerList().add(newPlayer);

                        // Clear the fields after successful addition
                        nameField.clear();
                        countryField.clear();
                        ageField.clear();
                        heightField.clear();
                        clubField.clear();
                        positionField.clear();
                        numberField.clear();
                        salaryField.clear();

                        statusLabel.setText("Player added successfully!");

                    } catch (NumberFormatException ex) {
                        statusLabel
                                .setText("Error: Invalid input! Ensure age, height, number, and salary are numbers.");
                    }
                });

                // Action for "Cancel" button
                cancelButton.setOnAction(e -> {
                    addPlayerStage.close();
                });
            }
        });
    }

    private void showSearchClubsMenu(Stage primaryStage) {
        // Search clubs options
        Stage searchStage = new Stage();
        searchStage.setTitle("Club Searching Options:");

        VBox searchVBox = new VBox(10);
        Label searchLabel = new Label("Choose Search Criteria:");

        Button byMaxSalaryButton = new Button("Player(s) with the maximum salary of a club");
        Button byMaxAgeButton = new Button("Player(s) with the maximum age of a club");
        Button byMaxHeightButton = new Button("Player(s) with the maximum height of a club");
        Button byTotalSalaryButton = new Button("Total yearly salary of a club");
        Button backButton = new Button("Back to Main Menu");

        // Set button actions
        byMaxSalaryButton.setOnAction(e -> searchByMaxSalary());
        byMaxAgeButton.setOnAction(e -> searchByMaxAge());
        byMaxHeightButton.setOnAction(e -> searchByMaxHeight());
        byTotalSalaryButton.setOnAction(e -> searchByTotalSalary());
        backButton.setOnAction(e -> searchStage.close());

        searchVBox.getChildren().addAll(searchLabel, byMaxSalaryButton, byMaxAgeButton, byMaxHeightButton,
                byTotalSalaryButton, backButton);
        Scene searchScene = new Scene(searchVBox, 400, 400);
        searchStage.setScene(searchScene);
        searchStage.show();
    }

    private void searchByName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("By Player Name");
        dialog.setHeaderText("Enter Player Name: ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            List<Player> foundPlayers = dataBase.searchByPlayerName(name);
            if (foundPlayers.isEmpty()) {
                showAlert("“No such player with this name");
            } else {
                displayPlayerInfo(foundPlayers);
            }
        });
    }

    private void searchByClubCountry() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("By Club and Country");
        dialog.setHeaderText("Enter Country Name: ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(country -> {
            TextInputDialog dialog2 = new TextInputDialog();
            // dialog2.setTitle("Search Clubs");
            dialog2.setHeaderText("Enter Club Name: ");
            Optional<String> result2 = dialog2.showAndWait();
            result2.ifPresent(club -> {
                List<Player> foundPlayers = dataBase.searchByClubAndCountry(country, club);
                if (foundPlayers.isEmpty()) {
                    showAlert("“No such player with this country and club");
                } else {
                    displayPlayerInfo(foundPlayers);
                }
            });
        });
    }

    private void searchByPosition() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("By Position");
        dialog.setHeaderText("Enter Position: ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(position -> {
            List<Player> foundPlayers = dataBase.searchByPosition(position);
            if (foundPlayers.isEmpty()) {
                showAlert("“No such player with this position");
            } else {
                displayPlayerInfo(foundPlayers);
            }
        });
    }

    private void searchBySalaryRange() {
        // First dialog for lower salary
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("By Salary Range");
        dialog.setHeaderText("Enter Minimum Salary: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(losalaryStr -> {
            double losalary = Double.parseDouble(losalaryStr);
            // Second dialog for higher salary
            TextInputDialog dialog2 = new TextInputDialog();
            dialog2.setHeaderText("Enter Maximum Salary: ");

            Optional<String> result2 = dialog2.showAndWait();
            result2.ifPresent(hisalaryStr -> {
                double hisalary = Double.parseDouble(hisalaryStr);
                // Call the method to search by salary range
                List<Player> foundPlayers = dataBase.searchBySalaryRange(losalary, hisalary);
                if (foundPlayers.isEmpty()) {
                    showAlert("“No such player with this weekly salary range");
                } else {
                    displayPlayerInfo(foundPlayers);
                }
            });
        });
    }

    private void displayCountryWiseCount() {
        // Get the country-wise player count from the database
        Map<String, Integer> countryWiseCount = dataBase.CountryWisePlayerCount();

        // Build a string with the country and player count
        StringBuilder countryCountDetails = new StringBuilder("Country-wise Player Count:\n");
        for (Map.Entry<String, Integer> entry : countryWiseCount.entrySet()) {
            countryCountDetails.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        // Show the result in an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Country-wise Player Count");
        alert.setHeaderText(null);
        alert.setContentText(countryCountDetails.toString());
        alert.showAndWait();
    }

    private void searchByMaxSalary() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Maximum salary of a club");
        dialog.setHeaderText("Enter Club Name: ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(ClubName -> {
            List<Player> foundPlayers = dataBase.MaximumSalaryOfAClub(ClubName);
            if (foundPlayers.isEmpty()) {
                showAlert("No such club with this name");
            } else {
                displayPlayerInfo(foundPlayers);
            }
        });
    }

    private void searchByMaxAge() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Maximum age of a club");
        dialog.setHeaderText("Enter Club Name: ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(ClubName -> {
            List<Player> foundPlayers = dataBase.MaximumAgeOfAClub(ClubName);
            if (foundPlayers.isEmpty()) {
                showAlert("No such club with this name");
            } else {
                displayPlayerInfo(foundPlayers);
            }
        });
    }

    private void searchByMaxHeight() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Maximum height of a club");
        dialog.setHeaderText("Enter Club Name: ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(ClubName -> {
            List<Player> foundPlayers = dataBase.MaximumHeightOfAClub(ClubName);
            if (foundPlayers.isEmpty()) {
                showAlert("No such club with this name");
            } else {
                displayPlayerInfo(foundPlayers);
            }
        });
    }

    private void searchByTotalSalary() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Total yearly salary of a club");
        dialog.setHeaderText("Enter Club Name: ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(ClubName -> {
            List<Player> players = dataBase.TotalSalaryOfAClub(ClubName);
            if (players.isEmpty()) {
                showAlert("No such club with this name");
            } else {
                int totalSalary = players.stream().mapToInt(Player::getWeekly_Salary).sum();
                showAlert("Total yearly salary of " + ClubName + " is " + totalSalary);
            }
        });
    }

    // Display player information
    private void displayPlayerInfo(List<Player> players) {
        // Create a TableView and define its columns
        TableView<Player> tableView = new TableView<>();

        // Define columns
        TableColumn<Player, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> countryColumn = new TableColumn<>("Country");
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<Player, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age_in_years"));

        TableColumn<Player, Double> heightColumn = new TableColumn<>("Height");
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height_in_meters"));

        TableColumn<Player, String> clubColumn = new TableColumn<>("Club");
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));

        TableColumn<Player, String> positionColumn = new TableColumn<>("Position");
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Player, Integer> numberColumn = new TableColumn<>("Jersey Number");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<Player, Integer> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("weekly_Salary"));

        // Add columns to the table
        Collections.addAll(tableView.getColumns(), nameColumn, countryColumn, ageColumn, heightColumn, clubColumn,
                positionColumn, numberColumn, salaryColumn);

        // Create an ObservableList and add players to it
        ObservableList<Player> playerData = FXCollections.observableArrayList(players);

        // Set the table's items
        tableView.setItems(playerData);

        // Create a scene with the TableView
        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox, 800, 600);

        // Create a new stage to display the TableView
        Stage infoStage = new Stage();
        infoStage.setTitle("Player Information");
        infoStage.setScene(scene);
        infoStage.show();
    }

    // Display alert message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void loadPlayersFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] playerData = line.split(",");
                Player player = new Player(
                        playerData[0].trim(),
                        playerData[1].trim(),
                        Integer.parseInt(playerData[2].trim()),
                        Double.parseDouble(playerData[3].trim()),
                        playerData[4].trim(),
                        playerData[5].trim(),
                        playerData[6].trim().isEmpty() ? 0 : Integer.parseInt(playerData[6].trim()),
                        Integer.parseInt(playerData[7].trim()));
                dataBase.getPlayerList().add(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAndExit(Stage primaryStage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName))) {
            for (Player player : dataBase.getPlayerList()) {
                writer.write(player.toCSV() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.close();
    }
}
