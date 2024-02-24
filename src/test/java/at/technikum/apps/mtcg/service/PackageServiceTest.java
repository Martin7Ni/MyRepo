package at.technikum.apps.mtcg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalAnswers.*;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Test;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.CardType;
import at.technikum.apps.mtcg.entity.ElementType;
import at.technikum.apps.mtcg.repository.CardRepository;

class PackageServiceTest {

	@Test
	void shouldSetTypeAndElement_whenSave() {

		//Arrange
		CardRepository cardRepository = mock(CardRepository.class);
		PackageService packageService = new PackageService(cardRepository);
		
		List<Card> packages = new ArrayList<>();
		packages.add(new Card("idtest", "WaterGoblin", 0));
		packages.add(new Card("idtest", "watergoblin", 0));
		packages.add(new Card("idtest", "FireSpell", 0));
		when(cardRepository.save(any())).then(returnsFirstArg());
		
		//Act
		packages = packageService.save(packages);
		
		//Assert
		assertEquals(ElementType.water, packages.get(0).getElementType());
		assertEquals(CardType.goblin, packages.get(0).getCardType());
		assertEquals(ElementType.water, packages.get(1).getElementType());
		assertEquals(CardType.goblin, packages.get(1).getCardType());
		assertEquals(ElementType.fire, packages.get(2).getElementType());
		assertEquals(CardType.spell, packages.get(2).getCardType());
		
	}

	@Test
	void shouldSetElementToRegular_whenSaveWithNewElement() {

		//Arrange
		CardRepository cardRepository = mock(CardRepository.class);
		PackageService packageService = new PackageService(cardRepository);
		
		List<Card> packages = new ArrayList<>();
		packages.add(new Card("idtest", "AirGoblin", 0));
		packages.add(new Card("idtest", "earthDragon", 0));
		when(cardRepository.save(any())).then(returnsFirstArg());
		
		//Act
		packages = packageService.save(packages);
		
		//Assert
		assertEquals(ElementType.regular, packages.get(0).getElementType());
		assertEquals(CardType.goblin, packages.get(0).getCardType());
		assertEquals(ElementType.regular, packages.get(1).getElementType());
		assertEquals(CardType.dragon, packages.get(1).getCardType());
		
	}

	@Test
	void shouldReturnNull_whenCardSaveNotWorking() {

		//Arrange
		CardRepository cardRepository = mock(CardRepository.class);
		PackageService packageService = new PackageService(cardRepository);
		
		List<Card> packages = new ArrayList<>();
		packages.add(new Card("idtest", "AirGoblin", 0));
		packages.add(new Card("idtest", "earthDragon", 0));
		when(cardRepository.save(any())).thenReturn(null);
		
		//Act
		packages = packageService.save(packages);
		
		//Assert
		assertEquals(null, packages);
		
	}
}
