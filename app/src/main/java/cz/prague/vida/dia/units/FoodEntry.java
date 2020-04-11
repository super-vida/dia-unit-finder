package cz.prague.vida.dia.units;

import java.math.BigDecimal;

public class FoodEntry {

	private String inputText;

	private String foodName;

	private BigDecimal quantity;

	private String unit;

	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	/**
	 * @return the foodName
	 */
	public String getFoodName() {
		return foodName;
	}

	/**
	 * @param foodName
	 *            the foodName to set
	 */
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit
	 *            the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FoodEntry [foodName=");
		builder.append(foodName);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unit=");
		builder.append(unit);
		builder.append("]");
		return builder.toString();
	}

	public boolean isContvertDemanded() {
		return quantity != null && unit != null;
	}

	public String getUnitFormated() {
		if (unit != null && unit.matches("^jednot.*")) {
			return "j";
		}
		return unit;
	}

	public boolean isUseUnits() {
		return "j".equals(getUnitFormated());
	}

}
