package cz.prague.vida.dia.units;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class TextViewFormatter.
 */
public class OutputFormatter {
	
	private static final int SEPARATOR_LENGTH = 45;
	
	private static String separator;
	
	static{
		StringBuilder sb = new StringBuilder("\n");
		for (int i = 0; i < SEPARATOR_LENGTH; i++) {
			sb.append("-");
		}
		sb.append("\n");
		separator = sb.toString();
	}

	/**
	 * Creates the formatter.
	 *
	 * @return the text view formatter
	 */
	public static OutputFormatter createFormatter() {
		return new OutputFormatter();
	}

	/**
	 * Format.
	 *
	 * @param foodstuffList
	 *            the foodstuff list
	 * @param foodEntry
	 *            the food entry
	 * @return the string
	 */
	public List<FoodPresenter> format(List<Foodstuff> foodstuffList, FoodEntry foodEntry) {
		List<FoodPresenter> list = new ArrayList<>();
		if (foodstuffList != null && !foodstuffList.isEmpty()) {

			for (Foodstuff stuff : foodstuffList) {
				StringBuilder sb = new StringBuilder();
				FoodPresenter foodPresenter = new FoodPresenter(stuff, null, null);

//				sb.append(stuff.getName());
//				sb.append(": ");
				sb.append(stuff.getQuantity());
				sb.append("j");
				sb.append(" = ");
				sb.append(stuff.getServing());
				sb.append(stuff.getUnits());
				sb.append("\n");

				foodPresenter.setServing(sb.toString());

				if (foodEntry.isContvertDemanded()) {
					sb = new StringBuilder();
					sb.append(foodEntry.getQuantity());
					sb.append(foodEntry.getUnitFormated() + " = ");
					if (foodEntry.isUseUnits()) {
						sb.append(foodEntry.getQuantity().multiply(stuff.getMultiplyValue()));
						sb.append(stuff.getUnits());
					}
					else {
						sb.append(foodEntry.getQuantity().divide(stuff.getServing(), 2, RoundingMode.HALF_UP));
						sb.append("j");
					}
					foodPresenter.setConversion(sb.toString());

					//sb.append(separator);
				}
				list.add(foodPresenter);
			}
			return list;
		}
		else {
			return new ArrayList<>();//foodEntry.getInputText() + ": " + "NENALEZENO!";
		}
	}
}
