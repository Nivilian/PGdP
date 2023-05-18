package pgdp.saleuine2;

import java.math.BigDecimal;

public class TradeOrder {

	private BigDecimal totalCost;
	private int currentWeight;

	public TradeOrder() {
		totalCost = BigDecimal.ZERO;
		currentWeight = 0;
	}

	public BigDecimal getCost() {
		return totalCost;
	}

	public int getCurrentWeight() {
		return currentWeight;
	}

	public boolean supplyOrder(PinguFood supply, BigDecimal cost) {
		// with the use of isOrderFulfilled we can reuse this method for subclasses
		if (isOrderFulfilled() || !supply.isEdible()) {
			return false;
		}
		totalCost = totalCost.add(cost);
		currentWeight += supply.getWeight();
		return true;
	}

	public boolean isOrderFulfilled() {
		// every PinguFood has positive weight -> 
		// currentWeight unequal to (or larger then) 0 means there was food delivered 
		return currentWeight != 0;
	}

	public String orderType() {
		return "Einzeln";
	}

	@Override
	public String toString() {
		// we don't have to override toString in subclasses because of using orderType() here
		// and only overriding orderType in the subclasses (polymorphism for the win)
		return "Die Bestellung(" + orderType() + ") hat ein Gesamtgewicht von " + currentWeight + "g und kostet "
				+ totalCost + "PD.";
	}
}
