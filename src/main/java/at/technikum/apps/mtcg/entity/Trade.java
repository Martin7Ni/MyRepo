package at.technikum.apps.mtcg.entity;

public class Trade {
	private String id;
	private String userid;
	private String cardtotrade;
	private String type;
	private double minimumdamage;
	private boolean traded = false;
	
	Trade(){
		
	}
	
	public Trade(String id, String userid, String cardtotrade, String type, double minimumdamage){
		this.setId(id);
		this.setUserid(userid);
		this.setCardtotrade(cardtotrade);
		this.setType(type);
		this.setMinimumdamage(minimumdamage);
	}
	public Trade(String id, String userid, String cardtotrade, String type, double minimumdamage, boolean traded){
		this.setId(id);
		this.setUserid(userid);
		this.setCardtotrade(cardtotrade);
		this.setType(type);
		this.setMinimumdamage(minimumdamage);
		this.traded = traded;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCardtotrade() {
		return cardtotrade;
	}

	public void setCardtotrade(String cardtotrade) {
		this.cardtotrade = cardtotrade;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getMinimumdamage() {
		return minimumdamage;
	}

	public void setMinimumdamage(double minimumdamage) {
		this.minimumdamage = minimumdamage;
	}

	public boolean isTraded() {
		return traded;
	}

	public void setTraded(boolean traded) {
		this.traded = traded;
	}
}
