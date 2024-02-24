package at.technikum.apps.mtcg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalAnswers.*;

import org.junit.jupiter.api.Test;

import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.UserRepository;

class DeckServiceTest {

	@Test
	void shoudlCallCardRepository_whenConfigureDeck() {

		//Arrange
		CardRepository cardRepository = mock(CardRepository.class);
		UserRepository userRepository = mock(UserRepository.class);
		DeckService deckService = new DeckService(cardRepository, userRepository);
		
		when(userRepository.getIdFromToken(any())).then(returnsFirstArg());
		when(cardRepository.cardsBelongToUser(any(), any())).thenReturn(true);
		
		//Act
		deckService.configureDeck(null, "tokentest");
		
		//Assert
		verify(cardRepository, times(1)).configureDeck(any(), any());
		verify(cardRepository, times(1)).showDeck(any());
		
	}

	@Test
	void shouldnotCallCardRepository_whenCardsDontBelongToUser() {

		//Arrange
		CardRepository cardRepository = mock(CardRepository.class);
		UserRepository userRepository = mock(UserRepository.class);
		DeckService deckService = new DeckService(cardRepository, userRepository);
		
		when(userRepository.getIdFromToken(any())).then(returnsFirstArg());
		when(cardRepository.cardsBelongToUser(any(), any())).thenReturn(false);
		
		//Act
		deckService.configureDeck(null, "tokentest");
		
		//Assert
		verify(cardRepository, times(0)).configureDeck(any(), any());
		verify(cardRepository, times(1)).showDeck(any());
		
	}

	@Test
	void shouldReturnNull_whenUserDoesntExist() {

		//Arrange
		CardRepository cardRepository = mock(CardRepository.class);
		UserRepository userRepository = mock(UserRepository.class);
		DeckService deckService = new DeckService(cardRepository, userRepository);
		
		when(userRepository.getIdFromToken(any())).thenReturn(null);
		when(cardRepository.cardsBelongToUser(any(), any())).thenReturn(true);
		
		//Act
		deckService.configureDeck(null, "tokentest");
		
		//Assert
		verify(cardRepository, times(0)).configureDeck(any(), any());
		verify(cardRepository, times(0)).showDeck(any());
		
	}
}
