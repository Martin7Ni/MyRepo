package at.technikum.apps.mtcg.service;

import java.util.List;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.UserRepository;

public class CardService {
	
	private final CardRepository cardRepository;
	private final UserRepository userRepository;
	
	public CardService(CardRepository cardRepository, UserRepository userRepository) {
		this.cardRepository = cardRepository;
		this.userRepository = userRepository;
	}
	
	public List<Card> showAll(String token) {
		String userId = userRepository.getIdFromToken(token);
		if(userId == null)return null;
		return cardRepository.showAllByUserid(userId);
	}
}
