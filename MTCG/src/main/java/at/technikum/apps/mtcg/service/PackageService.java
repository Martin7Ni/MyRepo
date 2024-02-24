package at.technikum.apps.mtcg.service;

import java.util.List;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.CardType;
import at.technikum.apps.mtcg.entity.ElementType;
import at.technikum.apps.mtcg.repository.CardRepository;

public class PackageService {
	
	private final CardRepository cardRepository;
	
	public PackageService(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	public List<Card> save(List<Card> packages){
		for (Card card: packages) {
			for (CardType cardType: CardType.values()) {
				if(card.getName().toLowerCase().contains(cardType.toString())) {
					card.setCardType(cardType);
					//System.out.println(cardType.toString());
					break;
				}
			}
			for (ElementType elementType: ElementType.values()) {
				if(card.getName().toLowerCase().contains(elementType.toString())) {
					card.setElementType(elementType);
					break;
				}
				card.setElementType(elementType.regular);
			}
			card.setUserId("none-"+System.nanoTime() / 1000l);
			if(cardRepository.save(card) == null) return null;
		}
		return packages;
	}
}
