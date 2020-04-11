package cz.prague.vida.dia.units;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Source {


    private static Map<String, Foodstuff> sourceMap = new HashMap();

    static {


        try(BufferedReader in = new BufferedReader(new InputStreamReader(Foodstuff.class.getResourceAsStream("food.csv"), "UTF8"))){

            String line = null;

            while ((line = in.readLine()) != null){
                String[] s = line.split(";");
                Foodstuff foodstuff = s.length == 6 ? new Foodstuff(s[0], s[1], s[2], s[3], s[4], s[5]) : new Foodstuff(s[0], s[1], s[2], s[3], s[4], "");
                System.out.println(foodstuff);
                sourceMap.put(foodstuff.getName().toLowerCase(), foodstuff);

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;


    }

    private static String reformatName(String name){
        String s = name.substring(name.length() -1 , name.length());
        if (s.equals("a")){
            return name.substring(0, name.length() - 1) + "y";
        }
        if (s.equals("y")){
            return name.substring(0, name.length() - 1) + "a";
        }
        return name;
    }


    public static List<Foodstuff> getFoodstuff(String name) {

        List<Foodstuff> list = new ArrayList<>();

        if (name == null) {
            return list;
        }
        String s = name.toLowerCase();
        for (String key : sourceMap.keySet()) {
            if (key.indexOf(s) > -1 || key.indexOf(reformatName(name)) > -1) {
                list.add(sourceMap.get(key));
            }

        }

        return list;
    }

    public static void main(String args[]){
        System.out.print("75g".trim().replaceAll("\\D", ""));
    }
}
