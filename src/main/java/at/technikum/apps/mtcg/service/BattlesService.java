package at.technikum.apps.mtcg.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.CardType;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.BattlesRepository;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.UserRepository;

public class BattlesService {
	
	private final UserRepository userRepository;
	private final CardRepository cardRepository;
	private final BattleSingleton battleSingleton;
	
	public BattlesService(UserRepository userRepository, 
			CardRepository cardRepository, 
			BattleSingleton battleSingleton) {
		this.cardRepository = cardRepository;
		this.userRepository = userRepository;
		this.battleSingleton = battleSingleton;
	}

	public String battle(String token) {
		
		String battlelog;
		String userId = userRepository.getIdFromToken(token);
		List<Card> deck = cardRepository.getDeckForBattle(userId);
		String winner = "";
		
		this.uniqueFeature(deck);
		
		System.out.println("start"+token);
		battleSingleton.findOpenBattles(deck, userId);
		battlelog = battleSingleton.getBattlelog();

		while (battlelog.isEmpty()) {
			System.out.println("43"+battlelog);
			battlelog = battleSingleton.getBattlelog();
		
		}
		
		winner = battleSingleton.getWinner();
		System.out.println("49"+winner);
		if(winner != "") {
			if(winner==userId) {
				userRepository.won(userId);
			} else {
				userRepository.lose(userId);
			}
		}
		//System.out.println(battleSingleton.getBattlelog()+token);
		System.out.println("finished"+token);
		return battlelog;
	}

	//unique feature
	//check if all cards of the deck have the same cardType to boost the moral 
	//which boosts their damage by +20
	private List<Card> uniqueFeature(List<Card> deck) {
		
		for(Card card : deck) {
			if(deck.get(0).getCardType() != card.getCardType())return deck;
		}
		
		//not sure if spells will get moral
		//if(deck.get(0).getCardType() == CardType.spell)return deck;
		
		for(Card card : deck) {
			card.setDamage(card.getDamage()+20);
		}
		System.out.println("moralbuff aquired!");
		return deck;
	}
	
}
