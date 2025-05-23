package data.dataBase;

import java.util.ArrayList;
import java.util.List;

public class Search {
    public static List<Player> SearchPlayerName(String name, List<Player>playerList){
        List<Player> plList=new ArrayList<>();
        for(Player p:playerList){
            if(name.equalsIgnoreCase(p.getName())){
                plList.add(p);
                break;
            }
        }
        return plList;
    }

    public static List<Player> SearchCountryName(String country,List<Player>playerList){
        List<Player> pllist=new ArrayList<>();
        for(Player p:playerList){
            if(country.equalsIgnoreCase(p.getCountry())){
                pllist.add(p);
            }
        }
        return pllist;
    }

    public static List<Player> SearchPosition(String position,List<Player>playerList){
        List<Player> pllist=new ArrayList<>();
        for(Player p:playerList){
            if(position.equalsIgnoreCase(p.getPosition())){
                pllist.add(p);
            }
        }
        return pllist;
    }

    public static List<Player> SearchSalary(double min,double max,List<Player>playerList){
        List<Player> pllist=new ArrayList<>();
        for(Player p:playerList){
            if(min<=p.getWeekly_Salary() && max>=p.getWeekly_Salary()){
                pllist.add(p);
            }
        }
        return pllist;
    }

    public static List<Player> MaxSalary(List<Player>playerList){
        double salary=0.0;
        for(Player p:playerList){
            if(p.getWeekly_Salary()>salary){
                salary= p.getWeekly_Salary();
            }
        }
        List<Player> pllist=new ArrayList<>();
        for(Player p:playerList){
            if(p.getWeekly_Salary()==salary){
                pllist.add(p);
            }
        }
        return pllist;
    }

    public static List<Player> MaxAge(List<Player>playerList){
        int age=0;
        for(Player p:playerList){
            if(p.getAge_in_years()>age){
                age= p.getAge_in_years();
            }
        }
        List<Player> pllist=new ArrayList<>();
        for(Player p:playerList){
            if(p.getAge_in_years()==age){
                pllist.add(p);
            }
        }
        return pllist;
    }

    public static List<Player> MaxHeight(List<Player>playerList){
        double height=0.0;
        for(Player p:playerList){
            if(p.getHeight_in_meters()>height){
                height= p.getHeight_in_meters();
            }
        }
        List<Player> pllist=new ArrayList<>();
        for(Player p:playerList){
            if(p.getHeight_in_meters()==height){
                pllist.add(p);
            }
        }
        return pllist;
    }

    public static double TotalSalary(List<Player>playerlist){
        double total=0.0;
        for(Player p:playerlist){
            total+=p.getWeekly_Salary()*52;
        }
        return total;
    }
}
