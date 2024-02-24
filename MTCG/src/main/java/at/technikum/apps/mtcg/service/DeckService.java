package at.technikum.apps.mtcg.service;

import java.util.List;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.UserRepository;

public class DeckService {
	
	private final CardRepository cardRepository;
	private final UserRepository userRepository;
	
	public DeckService(CardRepository cardRepository, UserRepository userRepository) {
		this.cardRepository = cardRepository;
		this.userRepository = userRepository;
	}
	
	public List<Card> showDeck(String token){
		String userId = userRepository.getIdFromToken(token);
		if(userId == null)return null;
		return cardRepository.showDeck(userId);
	}
	
	public List<Card> configureDeck(List<String> deck, String token){
		String userId = userRepository.getIdFromToken(token);
		if(userId == null)return null;
		if(cardRepository.cardsBelongToUser(deck, userId))cardRepository.configureDeck(deck, userId);
		return cardRepository.showDeck(userId);
	}
}
