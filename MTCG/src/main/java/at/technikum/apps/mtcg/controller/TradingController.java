package at.technikum.apps.mtcg.controller;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.Trade;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.service.TradingService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class TradingController extends Controller{
	
	private final TradingService tradingService;
	
	public TradingController(TradingService tradingService) {
		this.tradingService = tradingService;
	}

	@Override
	public boolean supports(String route) {
		return route.startsWith("/tradings");
	}

	@Override
	public Response handle(Request request) {
		if(request.getRoute().equals("/tradings")) {
			switch(request.getMethod()) {
				case "GET": return check(request);
				case "POST": return createOffer(request);
			}
		} else {
			switch(request.getMethod()) {
				case "POST": return takeOffer(request);
				case "DELETE": return deleteOffer(request);
			}
		}
		return status(HttpStatus.BAD_REQUEST);
	}

	private Response takeOffer(Request request) {

    	ObjectMapper objectMapper = new ObjectMapper();
    	String token = request.getAuthorization();
    	Response response = new Response();
    	String id = new String();
    	
    	id = request.getRoute().split("/")[2];
    	
    	Trade trade = tradingService.takeOffer(id, token, request.getBody());
    	
    	String tradeJson;
    	
    	if(trade == null) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setContentType(HttpContentType.APPLICATION_JSON);
			response.setBody("Error!");
			return response;
    	}

    	try {
    		tradeJson = objectMapper.writeValueAsString(trade);
    	} catch (JsonProcessingException e) {
    		throw new RuntimeException(e);
    	}
    	
		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.APPLICATION_JSON);
		response.setBody(tradeJson);
		return response;
	}

	private Response deleteOffer(Request request) {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	String id = new String();
    	String token = request.getAuthorization();
    	Response response = new Response();
    	
    	id = request.getRoute().split("/")[2];
    	
    	
    	String tradeJson;
    	Trade trade = tradingService.delete(id, token);
    	if(trade == null) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setContentType(HttpContentType.APPLICATION_JSON);
			response.setBody("Trade not found!");
			return response;
    	}
    	
    	try {
    		tradeJson = objectMapper.writeValueAsString(trade);
    	} catch (JsonProcessingException e) {
    		throw new RuntimeException(e);
    	}
    	
		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.APPLICATION_JSON);
		response.setBody(tradeJson);
		return response;
	}

	private Response check(Request request) {

    	ObjectMapper objectMapper = new ObjectMapper();
    	String token = request.getAuthorization();
    	Response response = new Response();
    	
    	List<Trade> trades= tradingService.check(token);
    	if(trades == null) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setContentType(HttpContentType.APPLICATION_JSON);
			response.setBody("Error!");
			return response;
    	}

    	String userJson;
    	
    	try {
    		userJson = objectMapper.writeValueAsString(trades);
    	} catch (JsonProcessingException e) {
    		throw new RuntimeException(e);
    	}
    	
		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.APPLICATION_JSON);
		response.setBody(userJson);
		return response;
	}
	
	private Response createOffer(Request request) {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	String token = request.getAuthorization();
    	Response response = new Response();
    	
    	Trade trade = tradingService.createOffer(token, request.getBody());
    	
    	String tradeJson;
    	
    	if(trade == null) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setContentType(HttpContentType.APPLICATION_JSON);
			response.setBody("Error!");
			return response;
    	}

    	try {
    		tradeJson = objectMapper.writeValueAsString(trade);
    	} catch (JsonProcessingException e) {
    		throw new RuntimeException(e);
    	}
    	
		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.APPLICATION_JSON);
		response.setBody(tradeJson);
		return response;
	}

}
