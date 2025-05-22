package data.dataBase;

import java.io.Serializable;

public class Player implements Serializable {
    private String Name;
    private String Country;
    private int Age_in_years;
    private double Height_in_meters;
    private String Club;
    private String Position; // can be Batsman,Baller,Wicketkeeper,All rounder
    private int Number; // Jersey number
    private int Weekly_Salary;

    public Player() {
    }

    public Player(String Name, String Country, int Age_in_years, double Height_in_meters, String Club, String Position,
            int Number, int Weekly_Salary) {
        this.Name = Name;
        this.Country = Country;
        this.Age_in_years = Age_in_years;
        this.Height_in_meters = Height_in_meters;
        this.Club = Club;
        this.Position = Position;
        this.Number = Number;
        this.Weekly_Salary = Weekly_Salary;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public int getAge_in_years() {
        return Age_in_years;
    }

    public void setAge_in_years(int Age_in_years) {
        this.Age_in_years = Age_in_years;
    }

    public double getHeight_in_meters() {
        return Height_in_meters;
    }

    public void setHeight_in_meters(double Height_in_meters) {
        this.Height_in_meters = Height_in_meters;
    }

    public String getClub() {
        return Club;
    }

    public void setClub(String Club) {
        this.Club = Club;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String Position) {
        this.Position = Position;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public int getWeekly_Salary() {
        return Weekly_Salary;
    }

    public void setWeekly_Salary(int Weekly_Salary) {
        this.Weekly_Salary = Weekly_Salary;
    }

    public String toCSV() {
        return Name + "," + Country + "," + Age_in_years + "," + Height_in_meters + "," + Club + "," + Position + ","
                + (Number == 0 ? "" : Number) + "," + Weekly_Salary;
    }

    @Override
    public String toString() {
        return "Name : " + Name + ", Country : " + Country + ", Age : " + Age_in_years + ", Height : "
                + Height_in_meters + ", Club : " + Club + ", Position : " + Position + ", Number : " + Number
                + ", Weekly Salary : " + Weekly_Salary;
    }
}
