package pgdp.saleuine2;

import static pgdp.PinguLib.*;

import java.math.BigDecimal;

public class PinguFoodLogistics {

	private TradeOrderQueue orderBook;

	private int wastedFoodAmount;
	private int wastedFoodWeight;
	private BigDecimal lostProfit;

	private BigDecimal ppgAnchovies;
	private BigDecimal ppgCrustaceans;
	private BigDecimal ppgSardines;

	public PinguFoodLogistics(BigDecimal priceAnchovies, BigDecimal priceCrustaceans, BigDecimal priceSardines) {
		ppgAnchovies = priceAnchovies;
		ppgCrustaceans = priceCrustaceans;
		ppgSardines = priceSardines;

		wastedFoodAmount = 0;
		wastedFoodWeight = 0;
		lostProfit = BigDecimal.ZERO;

		orderBook = new TradeOrderQueue();
	}

	public void acceptNewOrder(TradeOrder order) {
		orderBook.add(order);
	}

	public void clearOrderBook() {
		write("Es können " + orderBook.size() + " Bestellungen abgearbeitet werden.");
		TradeOrder currentOrder;
		PinguFood currentFood = null;
		while (!orderBook.isEmpty()) {
			currentOrder = orderBook.poll();
			// differentiate between AmountOrder and other Order-types to avoid wasting edible PinguFood
			if (currentOrder instanceof AmountOrder) {
				AmountOrder am = (AmountOrder) currentOrder;
				// supply AmountOrder with specific PinguFood
				while (!am.enoughAnchovies()) {
					currentFood = generateAnchovie();
					if (!am.supplyOrder(currentFood,
							ppgAnchovies.multiply(BigDecimal.valueOf(currentFood.getWeight())))) {
						registerUnusedFood(currentFood);
					}
				}
				while (!am.enoughCrustaceans()) {
					currentFood = generateCrustacean();
					if (!am.supplyOrder(currentFood,
							ppgCrustaceans.multiply(BigDecimal.valueOf(currentFood.getWeight())))) {
						registerUnusedFood(currentFood);
					}
				}
				while (!am.isOrderFulfilled()) {
					currentFood = generateSardine();
					if (!am.supplyOrder(currentFood,
							ppgSardines.multiply(BigDecimal.valueOf(currentFood.getWeight())))) {
						registerUnusedFood(currentFood);
					}
				}
			} else {
				// supply non-AmountOrder with random PinguFood
				while (!currentOrder.isOrderFulfilled()) {
					currentFood = generatePinguFood();
					if (!currentOrder.supplyOrder(currentFood,
							(currentFood instanceof Anchovie ? ppgAnchovies
									: currentFood instanceof Crustacean ? ppgCrustaceans : ppgSardines)
											.multiply(BigDecimal.valueOf(currentFood.getWeight())))) {
						registerUnusedFood(currentFood);
					}
				}
			}
			write(currentOrder.toString());
		}
	}

	private void registerUnusedFood(PinguFood food) {
		if (food == null) {
			return;
		}
		wastedFoodAmount++;
		wastedFoodWeight += food.getWeight();
		if (food instanceof Anchovie) {
			lostProfit = lostProfit.add(BigDecimal.valueOf(food.getWeight()).multiply(ppgAnchovies));
		} else if (food instanceof Crustacean) {
			lostProfit = lostProfit.add(BigDecimal.valueOf(food.getWeight()).multiply(ppgCrustaceans));
		} else {
			lostProfit = lostProfit.add(BigDecimal.valueOf(food.getWeight()).multiply(ppgSardines));
		}
	}

	public void printWasteStatistics() {
		write("Bisher konnten " + wastedFoodAmount + " Tiere mit einem Gesamtgewicht von " + wastedFoodWeight
				+ "g nicht verwertet werden.");
		write("Claudia und Karl-Heinz ist dadurch ein Profit von " + lostProfit + "PD entgangen.");
	}

	public static void main(String[] args) {
		PinguFoodLogistics market = new PinguFoodLogistics(BigDecimal.ONE, BigDecimal.valueOf(0.5),
				BigDecimal.valueOf(2));
		market.acceptNewOrder(new TradeOrder());
		market.acceptNewOrder(new WeightOrder(1000));
		market.acceptNewOrder(new AmountOrder(2, 2, 2));
		market.clearOrderBook();
		market.printWasteStatistics();
	}

	/**
	 * The following methods generate Anchovie, Crustacean or Sardine object
	 * WARNING: do NOT change these methods unless you want to fail the tests
	 */
	public static PinguFood generatePinguFood() {
		switch (randomInt(0, 2)) {
		case 0:
			return generateAnchovie();
		case 1:
			return generateCrustacean();
		case 2:
			return generateSardine();
		default:
			throw new SecurityException("You changed the code!");
		}
	}

	public static Anchovie generateAnchovie() {
		return new Anchovie(randomInt(0, 5), randomInt(1, 55));
	}

	public static Crustacean generateCrustacean() {
		return new Crustacean(randomInt(1, 10));
	}

	public static Sardine generateSardine() {
		return new Sardine(randomInt(0, 10), randomInt(20, 300), randomInt(1, 22));
	}
}
