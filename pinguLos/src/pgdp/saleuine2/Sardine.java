package pgdp.saleuine2;

public class Sardine extends PinguFood {

	private int length;

	private static final int MIN_AGE = 1;
	private static final int MIN_WEIGHT = 100;
	private static final int MIN_LENGTH = 14;

	public Sardine(int age, int weight, int length) {
		super(age, weight);
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	@Override
	public boolean isEdible() {
		return this.getAge() >= MIN_AGE && this.getWeight() >= MIN_WEIGHT && length >= MIN_LENGTH;
	}

	@Override
	public String toString() {
		// avoid duplicate code using super.toString
		return "Sardine(" + super.toString() + ", Länge: " + length + ")";
	}
}
