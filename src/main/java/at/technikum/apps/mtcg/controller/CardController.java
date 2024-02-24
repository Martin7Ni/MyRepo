package at.technikum.apps.mtcg.controller;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.service.CardService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class CardController extends Controller{
	
	private final CardService cardService;
	
	public CardController(CardService cardService) {
		this.cardService = cardService;
	}

	@Override
	public boolean supports(String route) {
		return route.startsWith("/cards");
	}

	@Override
	public Response handle(Request request) {
		
		switch(request.getMethod()) {
			case "GET": return showAll(request);
		}
		return status(HttpStatus.BAD_REQUEST);
	}

	public Response showAll(Request request) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<Card> cards = cardService.showAll(request.getAuthorization());
		String cardsJson = null;
		Response response = new Response();
		
		if(cards == null) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setContentType(HttpContentType.APPLICATION_JSON);
			response.setBody("Couldn't find any Cards!");
			return response;
		}
		
		try {
			cardsJson = objectMapper.writeValueAsString(cards);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		
		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.APPLICATION_JSON);
		response.setBody(cardsJson);
		return response;
	}
}
