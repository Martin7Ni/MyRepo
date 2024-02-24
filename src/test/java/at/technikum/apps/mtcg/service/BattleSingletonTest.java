package at.technikum.apps.mtcg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.CardType;
import at.technikum.apps.mtcg.entity.ElementType;

class BattleSingletonTest {

	@Test
	void shouldGetWinner_whenBattles() {
		
		//Arrange
		BattleSingleton battleSingleton = BattleSingleton.getInstance();
		
		List<Card> deck = new ArrayList<>();
		deck.add(new Card(null, null, 0));
		
		//Act
		battleSingleton.findOpenBattles(deck, "userId1test");
		battleSingleton.findOpenBattles(new ArrayList<>(), "userId2test");
		
		//Assert
		assertEquals("userId1test", battleSingleton.getWinner());
	}

	@Test
	void shouldDoubleTheDamage_whenCalcDamage() {
		
		//Arrange
		BattleSingleton battleSingleton = BattleSingleton.getInstance();
		
		Card card1 = new Card(null, null, 10, ElementType.water, CardType.kraken, null, false);
		Card card2 = new Card(null, null, 10, ElementType.fire, CardType.spell, null, false);
		
				
		//Act
		double answer1 = battleSingleton.calcDamageModification(card1, card2);
		
		//Assert
		assertEquals(20.0, answer1);
	}

	@Test
	void shouldnotDoubleTheDamage_whenCalcDamage() {
		
		//Arrange
		BattleSingleton battleSingleton = BattleSingleton.getInstance();
		
		Card card1 = new Card(null, null, 10, ElementType.fire, CardType.kraken, null, false);
		Card card2 = new Card(null, null, 10, ElementType.water, CardType.spell, null, false);
		
				
		//Act
		double answer1 = battleSingleton.calcDamageModification(card1, card2);
		double answer2 = battleSingleton.calcDamageModification(card2, card1);
		
		//Assert
		assertEquals(5.0, answer1);
		assertEquals(0.0, answer2);
	}
}