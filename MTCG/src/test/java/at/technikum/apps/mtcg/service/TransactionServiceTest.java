package at.technikum.apps.mtcg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.AdditionalAnswers.*;

import org.junit.jupiter.api.Test;

import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.UserRepository;

class TransactionServiceTest {

	@Test
	void shouldReturnTrue_whenAcquire() {

		//Arrange
		UserRepository userRepository = mock(UserRepository.class);
		CardRepository cardRepository = mock(CardRepository.class);
		TransactionService transactionService = new TransactionService(userRepository, cardRepository);
		
		List<String> cardIds = new ArrayList<>();
		cardIds.add("");
		when(userRepository.getUserIdForPackageTransaction(any())).then(returnsFirstArg());
		when(cardRepository.getFirstFiveCardId(any())).thenReturn(cardIds);
		when(cardRepository.acquire(any(), any())).thenReturn(true);
		when(userRepository.spendCoins(any())).thenReturn(true);
		
		//Act
		boolean answer = transactionService.acquire("");
		
		//Assert
		assertEquals(true, answer);
	}

	@Test
	void shouldReturnFalse_whenAcquireIsFalse() {

		//Arrange
		UserRepository userRepository = mock(UserRepository.class);
		CardRepository cardRepository = mock(CardRepository.class);
		TransactionService transactionService = new TransactionService(userRepository, cardRepository);
		
		List<String> cardIds = new ArrayList<>();
		cardIds.add("");
		when(userRepository.getUserIdForPackageTransaction(any())).then(returnsFirstArg());
		when(cardRepository.getFirstFiveCardId(any())).thenReturn(cardIds);
		//test when acquire returns false
		when(cardRepository.acquire(any(), any())).thenReturn(false);
		when(userRepository.spendCoins(any())).thenReturn(true);
		
		//Act
		boolean answer = transactionService.acquire("");
		
		//Assert
		assertEquals(false, answer);
	}
}
