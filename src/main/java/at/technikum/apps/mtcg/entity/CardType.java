package at.technikum.apps.mtcg.entity;

public enum CardType {
	goblin(0),
	dragon(1),
	elf(2),
	ork(4),
	wizzard(5),
	knight(7),
	spell(8),
	kraken(9),
	human(11);
	
	private int i;

	CardType(int i) {
		this.i = i;
	}
	
	public int getterI() {
		return this.i;
	}
}
