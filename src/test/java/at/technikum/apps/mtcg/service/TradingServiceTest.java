package at.technikum.apps.mtcg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalAnswers.*;

import org.junit.jupiter.api.Test;

import at.technikum.apps.mtcg.entity.Trade;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.TradingRepository;
import at.technikum.apps.mtcg.repository.UserRepository;

class TradingServiceTest {

	@Test
	void shouldSetParameters_whenCreateOffer() {

		//Arrange
		UserRepository userRepository = mock(UserRepository.class);
		CardRepository cardRepository = mock(CardRepository.class);
		TradingRepository tradingRepository = mock(TradingRepository.class);
		TradingService tradingService = new TradingService(userRepository, cardRepository, tradingRepository);
		
		String body = "{\"id\": \"tradeidtest\", \"CardToTrade\": \"cardidtest\", \"Type\": \"typetest\", \"Minimumdamage\": 15}";
		when(userRepository.getIdFromToken(any())).then(returnsFirstArg());
		when(cardRepository.undeckCard(any())).thenReturn(true);
		when(tradingRepository.createOffer(any())).then(returnsFirstArg());
		
		//Act
		Trade trade = tradingService.createOffer("userid", body);
		
		//Assert
		assertEquals("tradeidtest", trade.getId());
		assertEquals("cardidtest", trade.getCardtotrade());
		assertEquals("typetest", trade.getType());
		assertEquals(15, trade.getMinimumdamage());
		
	}
	
	@Test
	void shouldReturnNull_whenBodyIsNotCorrect() {

		//Arrange
		UserRepository userRepository = mock(UserRepository.class);
		CardRepository cardRepository = mock(CardRepository.class);
		TradingRepository tradingRepository = mock(TradingRepository.class);
		TradingService tradingService = new TradingService(userRepository, cardRepository, tradingRepository);
		
		String body = "{\"id\": \"tradeidtest\"";
		when(userRepository.getIdFromToken(any())).then(returnsFirstArg());
		when(cardRepository.undeckCard(any())).thenReturn(true);
		when(tradingRepository.createOffer(any())).then(returnsFirstArg());
		
		//Act
		Trade trade = tradingService.createOffer("userid", body);
		
		//Assert
		assertEquals(null, trade);
		
	}

	@Test
	void shouldReturnTrade_whenTakeOffer() {

		//Arrange
		UserRepository userRepository = mock(UserRepository.class);
		CardRepository cardRepository = mock(CardRepository.class);
		TradingRepository tradingRepository = mock(TradingRepository.class);
		TradingService tradingService = new TradingService(userRepository, cardRepository, tradingRepository);
		
		Trade trade = new Trade("tradeidtest", "token1test", "cardid1test", null, 0);
		when(userRepository.getIdFromToken(any())).then(returnsFirstArg());
		when(tradingRepository.getTrade(any(), any())).thenReturn(trade);
		when(cardRepository.tradeCards(any(), any(), any(), any())).thenReturn(true);
		
		//Act
		trade = tradingService.takeOffer("tradeidtest", "token2test", "\"cardid2test");
		
		//Assert
		verify(cardRepository, times(1)).tradeCards(any(), any(), any(), any());
		verify(cardRepository, times(1)).tradeCards( "token2test", "cardid2test", "token1test", "cardid1test");
		
	}

	@Test
	void shouldReturnFalse_whenIdWrong() {

		//Arrange
		UserRepository userRepository = mock(UserRepository.class);
		CardRepository cardRepository = mock(CardRepository.class);
		TradingRepository tradingRepository = mock(TradingRepository.class);
		TradingService tradingService = new TradingService(userRepository, cardRepository, tradingRepository);
		
		when(userRepository.getIdFromToken(any())).then(returnsFirstArg());
		when(tradingRepository.getTrade(any(), any())).thenReturn(null);
		when(cardRepository.tradeCards(any(), any(), any(), any())).thenReturn(true);
		
		//Act
		Trade trade = tradingService.takeOffer("tradeidtest", "tokentest", "\"cardidtest");
		
		//Assert
		assertEquals(null, trade);
		
	}
}
