package at.technikum.apps.mtcg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class UserController extends Controller {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

    @Override
    public boolean supports(String route) {
        return route.startsWith("/users");
    }

    @Override
    public Response handle(Request request) {
    	if(request.getRoute().contains("/users")) {
    		switch(request.getMethod()) {
    			case "POST": return create(request);
    			case "GET": return get(request);
    			case "PUT": return edit(request);
    		}
    	}

		return status(HttpStatus.BAD_REQUEST);
    }
    
    private Response edit(Request request) {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	String username = new String();
    	String token = request.getAuthorization();
    	Response response = new Response();
    	
    	username = request.getRoute().split("users/")[1];
    	
    	String userJson;

    	
    	User user = userService.update(username, token, request.getBody());
    	if(user == null) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setContentType(HttpContentType.APPLICATION_JSON);
			response.setBody("Error!");
			return response;
    	}
    	
    	try {
    		userJson = objectMapper.writeValueAsString(user);
    	} catch (JsonProcessingException e) {
    		throw new RuntimeException(e);
    	}
    	
		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.APPLICATION_JSON);
		response.setBody(userJson);
		return response;
    }
    
    private Response get(Request request) {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	String username = new String();
    	String token = request.getAuthorization();
    	Response response = new Response();
    	
    	username = request.getRoute().split("users/")[1];
    	
    	String userJson;
    	
    	User user = userService.get(username, token);
    	if(user == null) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setContentType(HttpContentType.APPLICATION_JSON);
			response.setBody("Error!");
			return response;
    	}
    	
    	try {
    		userJson = objectMapper.writeValueAsString(user);
    	} catch (JsonProcessingException e) {
    		throw new RuntimeException(e);
    	}
    	
		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.APPLICATION_JSON);
		response.setBody(userJson);
		return response;
    }

	private Response create(Request request) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		User user = null;
		try {
			user = objectMapper.readValue(request.getBody(), User.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		user = userService.save(user);
		
		String userJson = null;
		try {
			userJson = objectMapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		if(user == null) {
			return status(HttpStatus.BAD_REQUEST);
		}
		
		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.APPLICATION_JSON);
		response.setBody(userJson);
		
		return response;
	}
}
