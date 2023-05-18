package pgdp.saleuine2;

public class WeightOrder extends TradeOrder {

	private final int targetWeight;

	public WeightOrder(int targetWeight) {
		super();
		this.targetWeight = targetWeight;
	}

	@Override
	public boolean isOrderFulfilled() {
		return targetWeight <= this.getCurrentWeight();
	}

	@Override
	public String orderType() {
		return "Zielgewicht: " + targetWeight + "g";
	}
}
