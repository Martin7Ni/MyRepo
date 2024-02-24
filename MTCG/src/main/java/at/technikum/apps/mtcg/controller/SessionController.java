package at.technikum.apps.mtcg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.service.SessionService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class SessionController extends Controller{
	
	private final SessionService sessionService;

	public SessionController(SessionService sessionService) {
		this.sessionService = sessionService;
	}
	
	@Override
	public boolean supports(String route) {
		return route.startsWith("/sessions");
	}

	@Override
	public Response handle(Request request) {
		
		if(request.getRoute().equals("/sessions")) {
			switch(request.getMethod()) {
				case "POST": return login(request);
			}
			return status(HttpStatus.BAD_REQUEST);
		}
		return status(HttpStatus.BAD_REQUEST);
	}

	public Response login(Request request) {
		ObjectMapper objectMapper = new ObjectMapper();
		User user = null;
		try {
			user = objectMapper.readValue(request.getBody(), User.class);
		} catch(JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String tokenJson = sessionService.login(user);
		System.out.println(tokenJson);
		
		if(tokenJson == null) {
			return status(HttpStatus.NOT_FOUND);
		}
		
		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.APPLICATION_JSON);
		response.setBody("{\"token\":\""+sessionService.login(user)+"\"}");
		return response;
	}
}
