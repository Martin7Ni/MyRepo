package at.technikum.apps.mtcg.service;

import java.util.List;

import at.technikum.apps.mtcg.entity.Trade;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.TradingRepository;
import at.technikum.apps.mtcg.repository.UserRepository;

public class TradingService {
	
	private final UserRepository userRepository;
	private final CardRepository cardRepository;
	private final TradingRepository tradingRepository;
	
	public TradingService(UserRepository userRepository,
			CardRepository cardRepository,
			TradingRepository tradingRepository) {
		this.userRepository = userRepository;
		this.cardRepository = cardRepository;
		this.tradingRepository = tradingRepository;
	}

	public List<Trade> check(String token) {
		String userId = userRepository.getIdFromToken(token);
		//System.out.println(userId);
		if(userId == null)return null;
		return tradingRepository.check(userId);
	}

	public Trade createOffer(String token, String body) {
		if(body.split("\"").length<14)return null;
		String userId = userRepository.getIdFromToken(token);
		String id = body.split("\"")[3];
		String cardtotrade = body.split("\"")[7];
		String type = body.split("\"")[11];
		String s = body.split("\"")[14].replaceAll("\\D+","");
		double minimumdamage = Double.parseDouble(s);
		/*System.out.println(body);
		System.out.println(userId);
		System.out.println(id);
		System.out.println(cardtotrade);
		System.out.println(type);
		System.out.println(minimumdamage);*/
		Trade trade = new Trade(id, userId, cardtotrade, type, minimumdamage);
		
		if(!cardRepository.undeckCard(cardtotrade))return null;
		
		trade = tradingRepository.createOffer(trade);
		
		return trade;
	}

	public Trade delete(String id, String token) {
		String userId = userRepository.getIdFromToken(token);
		
		Trade trade = tradingRepository.delete(id, userId);
		
		if(cardRepository.deckCard(userId, trade.getCardtotrade())) {
			System.out.println(token+" got his card back");
		}
		
		return trade;
	}

	public Trade takeOffer(String id, String token, String body) {
		String userId1 = userRepository.getIdFromToken(token);
		String cardId1 = body.split("\"")[1];
		
		Trade trade = tradingRepository.getTrade(id, userId1);
		if(trade == null)return null;
		
		if(cardRepository.tradeCards(userId1, cardId1, trade.getUserid(), trade.getCardtotrade())) {
			System.out.println("trading success!");
		}
		return trade;
	}

}
