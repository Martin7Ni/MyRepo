package at.technikum.apps.mtcg.controller;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.service.DeckService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class DeckController extends Controller{
	
	private final DeckService deckService;
	
	public DeckController(DeckService deckService) {
		this.deckService = deckService;
	}

	@Override
	public boolean supports(String route) {
		return route.startsWith("/deck");
	}

	@Override
	public Response handle(Request request) {
		
		
		switch(request.getMethod()) {
			case "GET": 
				if(request.getRoute().contains("format=")) {
					switch(request.getRoute().split("format=")[1]) {
						case "plain": return showPlainDeck(request);
					}
				}
				return showDeck(request);
			case "PUT": return configureDeck(request);
		}
		return status(HttpStatus.BAD_REQUEST);
	}

	public Response showDeck(Request request) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<Card> deck = deckService.showDeck(request.getAuthorization());
		List<String> deckSimple = new ArrayList<String>();
		String cardsJson = null;
		Response response = new Response();
		
		if(deck == null) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setContentType(HttpContentType.APPLICATION_JSON);
			response.setBody("Coulnd't find deck!");
			return response;
		}
		
		for(Card card: deck) {
			deckSimple.add(card.getName()+" | "+card.getDamage());
		}
		
		try {
			cardsJson = objectMapper.writeValueAsString(deckSimple);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		
		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.APPLICATION_JSON);
		response.setBody(cardsJson);
		return response;
	}

	public Response showPlainDeck(Request request) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<Card> deck = deckService.showDeck(request.getAuthorization());
		String cardsJson = null;
		Response response = new Response();
		
		if(deck == null) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setContentType(HttpContentType.APPLICATION_JSON);
			response.setBody("Coulnd't find deck!");
			return response;
		}
		
		try {
			cardsJson = objectMapper.writeValueAsString(deck);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		
		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.APPLICATION_JSON);
		response.setBody(cardsJson);
		return response;
	}
	
	public Response configureDeck(Request request) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> deckByIds = null;
		try {
			deckByIds = objectMapper.readValue((request.getBody()), new TypeReference<List<String>>() {});
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		
		List<Card> deck= deckService.configureDeck(deckByIds, request.getAuthorization());
		
		String deckJson = null;
		try {
			deckJson = objectMapper.writeValueAsString(deck);
		} catch (JsonProcessingException e) {
			return status(HttpStatus.BAD_REQUEST);
		}
		
		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.APPLICATION_JSON);
		response.setBody(deckJson);
		return response;
	}
}
