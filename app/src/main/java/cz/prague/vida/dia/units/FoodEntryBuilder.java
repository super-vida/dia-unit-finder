package cz.prague.vida.dia.units;

import java.math.BigDecimal;

/**
 * The Class FoodEntryBuilder.
 */
public class FoodEntryBuilder {

    public static FoodEntryBuilder createBuilder() {
        return new FoodEntryBuilder();
    }


    public FoodEntry build(String text) {
        String[] s = text.split("\\s");

        FoodEntry foodEntry = new FoodEntry();
        foodEntry.setInputText(text);
        if (s != null && s.length > 0) {
            StringBuilder sbFoodName = new StringBuilder();
            boolean match = false;
            for (int i = 0; i < s.length; i++) {
                match = false;
                if (foodEntry.getQuantity() == null) {
                    match = parseQuantityAndUnit(s[i], foodEntry);
                }
                if (!match && foodEntry.getUnit() == null) {
                    match = parseUnit(s[i], foodEntry);
                }
                if (!match) {
                    sbFoodName.append(s[i]);
                    sbFoodName.append(" ");
                }
            }
            foodEntry.setFoodName(sbFoodName.toString().trim());
        }
        return foodEntry;
    }

    private boolean parseUnit(String string, FoodEntry foodEntry) {
        if (string.matches("^jednot.*|^gram.*|^ml")) {
            foodEntry.setUnit(string);
            return true;
        }
        String s = string.replaceAll("\\d", "");
        if (s.matches("g|ml")) {
            foodEntry.setUnit(s);
            return true;
        }
        return false;
    }

    private boolean parseQuantityAndUnit(String string, FoodEntry foodEntry) {
        try {
            foodEntry.setQuantity(new BigDecimal(string.replaceAll(",", ".")));
            return true;
        } catch (IllegalArgumentException e) {
            try {
                foodEntry.setQuantity(new BigDecimal(string.replaceAll("\\D", "")));
                parseUnit(string, foodEntry);
                return true;
            } catch (Exception e2) {
                return parseTextToNumber(string, foodEntry);
            }
        }
    }

    private boolean parseTextToNumber(String string, FoodEntry foodEntry) {
        switch (string) {
            case "jedna":
            case "jeden":
                foodEntry.setQuantity(BigDecimal.ONE);
                return true;
            case "dva":
            case "dvě":
                foodEntry.setQuantity(BigDecimal.valueOf(2));
                return true;
            case "tři":
                foodEntry.setQuantity(BigDecimal.valueOf(3));
                return true;
            case "čtyři":
                foodEntry.setQuantity(BigDecimal.valueOf(4));
                return true;
            case "pět":
                foodEntry.setQuantity(BigDecimal.valueOf(5));
                return true;
            case "šest":
                foodEntry.setQuantity(BigDecimal.valueOf(6));
                return true;
            case "sedm":
            case "sedum":
                foodEntry.setQuantity(BigDecimal.valueOf(7));
                return true;
            case "osm":
            case "osum":
                foodEntry.setQuantity(BigDecimal.valueOf(8));
                return true;
            case "devět":
                foodEntry.setQuantity(BigDecimal.valueOf(9));
                return true;
            default:
                return false;
        }

    }
}
