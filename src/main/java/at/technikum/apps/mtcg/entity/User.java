package at.technikum.apps.mtcg.entity;

import java.util.ArrayList;

public class User {
	private String id;
	private String username;
	private String password;
	private ArrayList<Card> stack;
	private int coins = 20;
	private ArrayList<Card> deck;
	private int eloValue = 100;
	private int win = 0;
	private int loss = 0;
	private String name = "";
	private String bio = "";
	private String image;
	private String token;
	
	public User() {
		
	}
	
	public User(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}


	public User(String id, String username, String password, int coins, int eloValue, int win, int loss, 
			String name, String bio, String image, String token) {
		this.setId(id);
		this.setUsername(username);
		this.setPassword(password);
		this.coins = coins;
		this.eloValue = eloValue;
		this.win = win;
		this.setLoss(loss);
		this.setName(name);
		this.setBio(bio);
		this.setImage(image);
		this.setToken(token);
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void addCard(Card card) {
		this.stack.add(card);
	}

	public void removeCard(Card oldCard, Card newCard) {
		this.stack.remove(oldCard);
		this.stack.add(newCard);
	}
	
	public int getCoin() {
		return this.coins;
	}
	
	public int getEloValue() {
		return this.eloValue;
	}
	
	public int getWin() {
		return this.win;
	}
	
	public void oneWin() {
		this.win++;
		this.eloValue += 3;
	}
	
	public void oneLoss() {
		this.setLoss(this.getLoss() + 1);
		this.eloValue -= 5;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public int getLoss() {
		return loss;
	}

	public void setLoss(int loss) {
		this.loss = loss;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
