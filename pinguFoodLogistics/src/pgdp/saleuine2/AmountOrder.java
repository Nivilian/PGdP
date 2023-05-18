package pgdp.saleuine2;

import java.math.BigDecimal;

public class AmountOrder extends TradeOrder {

	private final int targetAmountAnchovies;
	private final int targetAmountCrustaceans;
	private final int targetAmountSardines;

	private int currentAmountAnchovies;
	private int currentAmountCrustaceans;
	private int currentAmountSardines;

	public AmountOrder(int targetAmountAnchovies, int targetAmountCrustaceans, int targetAmountSardines) {
		super();
		this.targetAmountAnchovies = targetAmountAnchovies;
		this.targetAmountCrustaceans = targetAmountCrustaceans;
		this.targetAmountSardines = targetAmountSardines;

		currentAmountAnchovies = 0;
		currentAmountCrustaceans = 0;
		currentAmountSardines = 0;
	}

	@Override
	public boolean supplyOrder(PinguFood supply, BigDecimal cost) {
		// supply has to be differentiated
		// sequence of conditions is important! first calling super.supplyOrder possibly adds weight and cost, 
		// even if targetAmount for that PinguFood is already reached
		if (supply instanceof Anchovie && targetAmountAnchovies != currentAmountAnchovies
				&& super.supplyOrder(supply, cost)) {
			currentAmountAnchovies++;
			return true;
		} else if (supply instanceof Crustacean && targetAmountCrustaceans != currentAmountCrustaceans
				&& super.supplyOrder(supply, cost)) {
			currentAmountCrustaceans++;
			return true;
		} else if (supply instanceof Sardine && targetAmountSardines != currentAmountSardines
				&& super.supplyOrder(supply, cost)) {
			currentAmountSardines++;
			return true;
		} else {
			return false;
		}
	}

	public boolean enoughAnchovies() {
		return targetAmountAnchovies <= currentAmountAnchovies;
	}

	public boolean enoughCrustaceans() {
		return targetAmountCrustaceans <= currentAmountCrustaceans;
	}

	@Override
	public boolean isOrderFulfilled() {
		// == instead of <= would work too
		return targetAmountAnchovies <= currentAmountAnchovies && targetAmountCrustaceans <= currentAmountCrustaceans
				&& targetAmountSardines <= currentAmountSardines;
	}

	@Override
	public String orderType() {
		return "Anzahl: [" + targetAmountAnchovies + "," + targetAmountCrustaceans + "," + targetAmountSardines + "]";
	}
}
