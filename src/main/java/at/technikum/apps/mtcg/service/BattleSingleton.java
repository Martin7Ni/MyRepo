package at.technikum.apps.mtcg.service;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.CardType;
import at.technikum.apps.mtcg.entity.ElementType;

public class BattleSingleton {
	private static BattleSingleton battleSingleton;
	private List<Card> deckPlayer1;
	private List<Card> deckPlayer2;
	private String userIdPlayer1;
	private String userIdPlayer2;
	private static String battlelog = new String();
	private static ReentrantLock mutex = new ReentrantLock();
	private String winner = "";
	private int maxRounds = 100;
	private int countToReset = 2;
	
	private BattleSingleton() {
	}
	
	public static BattleSingleton getInstance() {
		if(battleSingleton == null) {
			try {
				mutex.lock();
				if(battleSingleton == null) {
					battleSingleton = new BattleSingleton();
				}
			} finally {
				mutex.unlock();
			}
		}
		return battleSingleton;
	}
	
	public boolean isBattlelogEmpty() {
		if(battlelog.isEmpty()) {
			return true;
		}
		return false;
	}
	public String getBattlelog() {
		return this.battlelog;
	}
	
	public void findOpenBattles(List<Card> deck, String userId) {
		try {
			mutex.lock();
			if(deckPlayer2 != null)return;
			if(deckPlayer1 == null) {
				this.winner = "";
				this.deckPlayer1 = deck;
				this.userIdPlayer1 = userId;
				System.out.println("53 player1 set");
			} else {
				this.deckPlayer2 = deck;
				this.userIdPlayer2 = userId;
				System.out.println("57 player2 set");
				battle();
			}
		} finally {
			mutex.unlock();
		}
		return;
	}
	
	private void battle() {
		int cardIndexPlayer1, cardIndexPlayer2;
		String battlelog = "";
		//System.out.println((int)(Math.random() *deckPlayer1.size()));
		while(maxRounds > 0 && !deckPlayer1.isEmpty() && !deckPlayer2.isEmpty()) {
			maxRounds--;
			String battlelogRound = "";
			cardIndexPlayer1 = ((int)(Math.random() * deckPlayer1.size()));
			cardIndexPlayer2 = ((int)(Math.random() * deckPlayer2.size()));
			System.out.println(deckPlayer1.size()+"--"+deckPlayer2.size());
			double damageCardPlayer1;
			double damageCardPlayer2;
			
			//set the players with their cards to use in this round
			battlelogRound = "PlayerA: "+deckPlayer1.get(cardIndexPlayer1).getName()+
							"("+deckPlayer1.get(cardIndexPlayer1).getDamage()+
							" Damage) vs PlayerB: "+deckPlayer2.get(cardIndexPlayer2).getName()+
							"("+deckPlayer2.get(cardIndexPlayer2).getDamage()+
							" Damage)";
			
			//calc the damage modifications
			damageCardPlayer1 = this.calcDamageModification(deckPlayer1.get(cardIndexPlayer1), 
						deckPlayer2.get(cardIndexPlayer2));
			damageCardPlayer2 = this.calcDamageModification(deckPlayer2.get(cardIndexPlayer2), 
						deckPlayer1.get(cardIndexPlayer1));
			
			//set the damage modifications
			battlelogRound = battlelogRound+" => "+ damageCardPlayer1+
							" VS "+damageCardPlayer2;
			
			
			//calc the winner 
			if(damageCardPlayer1 == damageCardPlayer2) {
				battlelogRound = battlelogRound+" => Draw (no action)\n";
				System.out.println(battlelogRound);
				battlelog = battlelog + battlelogRound;
				continue;
			}
			if(damageCardPlayer1 > damageCardPlayer2) {
				battlelogRound = battlelogRound+" => PlayerA:"+
							deckPlayer1.get(cardIndexPlayer1).getName()+
							" wins\n";
				deckPlayer1.add(deckPlayer2.remove(cardIndexPlayer2));
				System.out.println(battlelogRound);
				battlelog = battlelog + battlelogRound;
				continue;
			}
			if(damageCardPlayer1 < damageCardPlayer2) {
				battlelogRound = battlelogRound+" => PlayerB:"+
							deckPlayer2.get(cardIndexPlayer2).getName()+
							" wins\n";
				deckPlayer2.add(deckPlayer1.remove(cardIndexPlayer1));
				System.out.println(battlelogRound);
				battlelog = battlelog + battlelogRound;
				continue;
			}
			
			
		}
		if(deckPlayer2.isEmpty())winner = this.userIdPlayer1;
		if(deckPlayer1.isEmpty())winner = this.userIdPlayer2;
		//winner = this.userIdPlayer1;
		System.out.println("winner is "+this.winner);
		this.battlelog = battlelog;
		System.out.println(this.battlelog);
	}
	
	public String getWinner() {
		try {
			mutex.lock();
			countToReset--;
			System.out.println("winner checked");
			if(countToReset <= 0) {
				countToReset = 2;
				deckPlayer1 = null;
				deckPlayer2 = null;
				userIdPlayer1 = "";
				userIdPlayer2 = "";
				battlelog = new String();
				maxRounds = 100;
				System.out.println("reset");
			}
		} finally {
			mutex.unlock();
		}
		return winner;
	}
	
	public double calcDamageModification(Card card1, Card card2) {
		double damage = card1.getDamage();
		System.out.println(card1.getElementType().getterI()+"-"+card2.getElementType().getterI());
		System.out.println((card1.getElementType().getterI()-card2.getElementType().getterI()+6)%3);
		
		//check elements
		if(card1.getCardType() == CardType.spell || card2.getCardType() == CardType.spell) {
			switch((card1.getElementType().getterI() - card2.getElementType().getterI()+6)%3) {
				//same as case(-1)
				case(2):
					damage = damage / 2;
					break;
				case(0):
					//nothing to be done because both have the same element
					break;
				case(1):
					damage = damage * 2;
					break;
				default:
					break;
			}
		}
		
		//check type
		switch((card1.getCardType().getterI() - card2.getCardType().getterI()+24) % 12) {
			case(11):
				if(card1.getCardType() == CardType.knight) {
					if(card2.getElementType() != ElementType.water) {
						break;
					}
				}
				if(card1.getCardType() == CardType.dragon) {
					if(card2.getElementType() != ElementType.fire) {
						break;
					}
				}
				damage = 0;
				break;
			case(0):
				break;
			case(1):
				break;
			default:
				break;
		}
		
		return damage;
	}
}
