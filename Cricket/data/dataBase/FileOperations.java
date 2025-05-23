package data.dataBase;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

public class FileOperations {
    private static final String INPUT_FILE_NAME = "players.txt";
    private static final String OUTPUT_FILE_NAME = "players.txt";

    public static List<Player> readFromFile() throws IOException {
        List<Player> playerList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            String[] tokens = line.split(",");
            Player p = new Player();
            p.setName(tokens[0]);
            p.setCountry(tokens[1]);
            p.setAge_in_years(Integer.parseInt(tokens[2]));
            p.setHeight_in_meters(Double.parseDouble(tokens[3]));
            p.setClub(tokens[4]);
            p.setPosition(tokens[5]);
            p.setNumber(Integer.parseInt(tokens[6]));
            p.setWeekly_Salary(Integer.parseInt(tokens[7]));
            playerList.add(p);
        }
        br.close();
        return playerList;
    }

    public static void writeToFile(List<Player> playerList) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        for (Player p : playerList) {
            bw.write(p.getName() + "," + p.getCountry() + "," + p.getAge_in_years() + "," + p.getHeight_in_meters()
                    + "," + p.getClub() + "," + p.getPosition() + "," + p.getNumber() + "," + p.getWeekly_Salary());
            bw.write("\n");
        }
        bw.close();
    }
}