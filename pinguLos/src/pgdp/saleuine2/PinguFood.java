package pgdp.saleuine2;

public class PinguFood {

	private int age;
	private int weight;

	public PinguFood(int age, int weight) {
		this.age = age;
		this.weight = weight;
	}

	public int getAge() {
		return age;
	}

	public int getWeight() {
		return weight;
	}

	public boolean isEdible() {
		// standard return for all, possibly unknown PinguFood
		return false;
	}

	@Override
	public String toString() {
		return "Alter: " + age + " Jahre, Gewicht: " + weight + "g";
	}
}
