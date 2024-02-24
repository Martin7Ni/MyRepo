package at.technikum.apps.mtcg.entity;

public enum ElementType {
	water(0),
	regular(1),
	fire(2);

	private int i;
	
	ElementType(int i) {
		this.i = i;
	}
	
	public int getterI() {
		return this.i;
	}
}
