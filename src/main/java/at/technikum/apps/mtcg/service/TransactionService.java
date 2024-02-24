package at.technikum.apps.mtcg.service;

import java.util.List;

import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.UserRepository;

public class TransactionService {
	
	private final UserRepository userRepository;
	private final CardRepository cardRepository;
	
	public TransactionService(UserRepository userRepository, CardRepository cardRepository) {
		this.userRepository = userRepository;
		this.cardRepository = cardRepository;
	}
	
	public boolean acquire(String token) {
		String userId = userRepository.getUserIdForPackageTransaction(token);
		if(userId == null)return false;
		List<String> cardIds = cardRepository.getFirstFiveCardId(userId);
		if(cardIds == null)return false;
		for(String cardId: cardIds) {
			if(!cardRepository.acquire(cardId, userId))return false;
		}
		if(!userRepository.spendCoins(token))return false;
		return true;
	}
}
