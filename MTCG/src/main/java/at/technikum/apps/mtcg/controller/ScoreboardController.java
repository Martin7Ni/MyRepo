package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.service.ScoreboardService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class ScoreboardController extends Controller{
	
	private final ScoreboardService scoreboardService;
	
	public ScoreboardController(ScoreboardService scoreboardService) {
		this.scoreboardService = scoreboardService;
	}

	@Override
	public boolean supports(String route) {
		return route.startsWith("/scoreboard");
	}

	@Override
	public Response handle(Request request) {
		if(request.getRoute().contains("/scoreboard")) {
			switch(request.getMethod()) {
				case "GET": return scoreboard(request);
			}
		}
		return status(HttpStatus.BAD_REQUEST);
	}

	private Response scoreboard(Request request) {
		
    	String token = request.getAuthorization();
		Response response = new Response();
		
		String stats = scoreboardService.scoreboard(token);
		if(stats == null) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setContentType(HttpContentType.APPLICATION_JSON);
			response.setBody("Error!");
			return response;
		}

		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.TEXT_PLAIN);
		response.setBody(stats);
		return response;
	}

}
