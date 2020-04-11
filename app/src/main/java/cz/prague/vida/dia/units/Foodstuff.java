package cz.prague.vida.dia.units;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The Class Foodstuff.
 */
public class Foodstuff {

	private String group;
	private String name;
	private BigDecimal serving;
	private BigDecimal quantity;
	private String units;
	private String description;

	/**
	 * Instantiates a new foodstuff.
	 *
	 * @param group
	 *            the group
	 * @param name
	 *            the name
	 * @param serving
	 *            the serving
	 * @param units
	 *            the units
	 * @param quantity
	 *            the quantity
	 */
	public Foodstuff(String group, String name, String serving, String units, String quantity, String description) {
		this.group = group;
		this.name = name;
		this.serving = createBigDecimal(serving);
		this.units = units.trim();
		this.quantity = createBigDecimal(quantity);
		normalizeQuantity();
		this.description = description;
	}

	public Foodstuff(String group, String name, BigDecimal serving, String units, BigDecimal quantity) {
		this.group = group;
		this.name = name;
		this.serving = serving;
		this.quantity = quantity;
		this.units = units;
	}



	private void normalizeQuantity() {
		if (quantity != null && !quantity.equals(BigDecimal.ZERO)) {
			if (quantity.equals(BigDecimal.ONE)) {
				return;
			}
			if (quantity.intValue() < 1) {
				BigDecimal bd = BigDecimal.ONE.divide(quantity, RoundingMode.HALF_UP);
				quantity = BigDecimal.ONE;
				serving = serving.multiply(bd);
			}
			if (quantity.intValue() > 1) {
				serving = serving.divide(quantity, RoundingMode.HALF_UP);
				quantity = BigDecimal.ONE;
			}
		}
	}

	private BigDecimal createBigDecimal(String number) {
		if (number != null && number.length() > 0) {
			return new BigDecimal(number.trim().replace(",", "."));
		}
		return BigDecimal.ZERO;
	}

	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Sets the group.
	 *
	 * @param group
	 *            the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	public Foodstuff addGroup(String group) {
		this.group = group;
		return this;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Foodstuff addName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Gets the serving.
	 *
	 * @return the serving
	 */
	public BigDecimal getServing() {
		return serving;
	}

	/**
	 * Sets the serving.
	 *
	 * @param serving
	 *            the new serving
	 */
	public void setServing(BigDecimal serving) {
		this.serving = serving;
	}

	public Foodstuff addServing(BigDecimal serving) {
		this.serving = serving;
		return this;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getMultiplyValue() {
		return getQuantity().multiply(getServing());
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity
	 *            the new quantity
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the units.
	 *
	 * @return the units
	 */
	public String getUnits() {
		return units;
	}

	/**
	 * Sets the units.
	 *
	 * @param units
	 *            the new units
	 */
	public void setUnits(String units) {
		this.units = units;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Foodstuff [group=");
		builder.append(group);
		builder.append(", name=");
		builder.append(name);
		builder.append(", serving=");
		builder.append(serving);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", units=");
		builder.append(units);
		builder.append("]");
		return builder.toString();
	}

}
