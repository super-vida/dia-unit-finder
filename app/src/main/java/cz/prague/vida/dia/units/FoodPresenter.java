package cz.prague.vida.dia.units;

public class FoodPresenter {

    private Foodstuff foodstuff;

    private String serving;

    private String conversion;

    public FoodPresenter(Foodstuff foodstuff, String serving, String conversion) {
        this.foodstuff = foodstuff;
        this.serving = serving;
        this.conversion = conversion;
    }

    public Foodstuff getFoodstuff() {
        return foodstuff;
    }

    public void setFoodstuff(Foodstuff foodstuff) {
        this.foodstuff = foodstuff;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public String getConversion() {
        return conversion;
    }

    public void setConversion(String conversion) {
        this.conversion = conversion;
    }
}
