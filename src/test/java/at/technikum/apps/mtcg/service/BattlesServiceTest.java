package at.technikum.apps.mtcg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.AdditionalAnswers.*;

import org.junit.jupiter.api.Test;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.CardType;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.UserRepository;

class BattlesServiceTest {

	@Test
	void shouldCalluserRepositoryWon_whenBattle() {

		//Arrange
		CardRepository cardRepository = mock(CardRepository.class);
		UserRepository userRepository = mock(UserRepository.class);
		BattleSingleton battleSingleton= mock(BattleSingleton.class);
		BattlesService battlesService = new BattlesService(userRepository, cardRepository, battleSingleton);
		
		List<Card> deck = new ArrayList<>();
		deck.add(new Card(null, null, 0, null, CardType.human, null, false));
		deck.add(new Card(null, null, 0, null, CardType.human, null, false));
		deck.add(new Card(null, null, 0, null, CardType.human, null, false));
		deck.add(new Card(null, null, 0, null, CardType.human, null, false));
		
		when(userRepository.getIdFromToken(any())).then(returnsFirstArg());
		when(cardRepository.getDeckForBattle(any())).thenReturn(deck);
		when(battleSingleton.getBattlelog()).thenReturn("battlelogtest");
		when(battleSingleton.getWinner()).thenReturn("winnertest");
		String userId = "winnertest";
		
		//Act
		battlesService.battle(userId);
		
		//Assert
		verify(userRepository, times(1)).won(userId);
		verify(userRepository, times(0)).lose(userId);
		
	}

	@Test
	void shouldCalluserRepositoryLose_whenBattle() {

		//Arrange
		CardRepository cardRepository = mock(CardRepository.class);
		UserRepository userRepository = mock(UserRepository.class);
		BattleSingleton battleSingleton= mock(BattleSingleton.class);
		BattlesService battlesService = new BattlesService(userRepository, cardRepository, battleSingleton);
		
		List<Card> deck = new ArrayList<>();
		deck.add(new Card(null, null, 0, null, CardType.human, null, false));
		deck.add(new Card(null, null, 0, null, CardType.human, null, false));
		deck.add(new Card(null, null, 0, null, CardType.human, null, false));
		deck.add(new Card(null, null, 0, null, CardType.human, null, false));
		
		when(userRepository.getIdFromToken(any())).then(returnsFirstArg());
		when(cardRepository.getDeckForBattle(any())).thenReturn(deck);
		when(battleSingleton.getBattlelog()).thenReturn("battlelogtest");
		when(battleSingleton.getWinner()).thenReturn("losertest");
		String userId = "winnertest";
		
		//Act
		battlesService.battle(userId);
		
		//Assert
		verify(userRepository, times(0)).won(userId);
		verify(userRepository, times(1)).lose(userId);
		
	}

	@Test
	void shouldnotCalluserRepository_whenBattle() {

		//Arrange
		CardRepository cardRepository = mock(CardRepository.class);
		UserRepository userRepository = mock(UserRepository.class);
		BattleSingleton battleSingleton= mock(BattleSingleton.class);
		BattlesService battlesService = new BattlesService(userRepository, cardRepository, battleSingleton);
		
		List<Card> deck = new ArrayList<>();
		deck.add(new Card(null, null, 0, null, CardType.human, null, false));
		deck.add(new Card(null, null, 0, null, CardType.human, null, false));
		deck.add(new Card(null, null, 0, null, CardType.human, null, false));
		deck.add(new Card(null, null, 0, null, CardType.human, null, false));
		
		when(userRepository.getIdFromToken(any())).then(returnsFirstArg());
		when(cardRepository.getDeckForBattle(any())).thenReturn(deck);
		when(battleSingleton.getBattlelog()).thenReturn("battlelogtest");
		when(battleSingleton.getWinner()).thenReturn("");
		String userId = "winnertest";
		
		//Act
		battlesService.battle(userId);
		
		//Assert
		verify(userRepository, times(0)).won(userId);
		verify(userRepository, times(0)).lose(userId);
		
	}
}
