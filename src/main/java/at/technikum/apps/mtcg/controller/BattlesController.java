package at.technikum.apps.mtcg.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.service.BattlesService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class BattlesController extends Controller{
	
	private final BattlesService battlesService;
	
	public BattlesController(BattlesService battlesService) {
		this.battlesService = battlesService;
	}

	@Override
	public boolean supports(String route) {
		return route.startsWith("/battles");
	}

	@Override
	public Response handle(Request request) {
		
		switch(request.getMethod()) {
			case "POST": return battle(request);
		}
		return status(HttpStatus.BAD_REQUEST);
	}

	private Response battle(Request request) {
		
		String token = request.getAuthorization();
		String battlelog = battlesService.battle(token);
		Response response = new Response();
		
		if(battlelog == null) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setContentType(HttpContentType.APPLICATION_JSON);
			response.setBody("Error!");
			return response;
		}
		
		
		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.APPLICATION_JSON);
		response.setBody(battlelog);
		return response;
	}

}
