package at.technikum.apps.mtcg.entity;

public class Card {
	private String id;
	private String name;
	private double damage;
	private ElementType elementType;
	private CardType cardType;
	private String userId;
	private boolean deck = false;

	Card(){
		//this.elementType = ElementType.water;
	}

	public Card(String id, String name, int damage){
		this.id = id;
		this.setName(name);
		this.setDamage(damage);
	}
	
	public Card(String id, String name, int damage, ElementType elementType, CardType cardType, String userId, boolean deck){
		this.id = id;
		this.setName(name);
		this.setDamage(damage);
		this.setElementType(elementType);
		this.setCardType(cardType);
		this.setUserId(userId);
		this.setDeck(deck);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public ElementType getElementType() {
		return elementType;
	}

	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean getDeck() {
		return deck;
	}

	public void setDeck(boolean deck) {
		this.deck = deck;
	}
}
