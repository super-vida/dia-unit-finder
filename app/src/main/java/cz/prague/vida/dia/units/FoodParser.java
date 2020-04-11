package cz.prague.vida.dia.units;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FoodParser {
	public static void main(String[] args) throws IOException {

		try(BufferedReader in = new BufferedReader(new InputStreamReader(Foodstuff.class.getResourceAsStream("food.csv"), "UTF8"))){
			
			String line = null;
			
			while ((line = in.readLine()) != null){
				String[] s = line.split(";");
				Foodstuff foodstuff = s.length == 6 ? new Foodstuff(s[0], s[1], s[2], s[3], s[4], s[5]) : new Foodstuff(s[0], s[1], s[2], s[3], s[4], "");
				System.out.println(foodstuff);
			
			}
			
		};

	}

}
