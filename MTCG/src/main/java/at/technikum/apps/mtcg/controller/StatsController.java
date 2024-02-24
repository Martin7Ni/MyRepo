package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.service.StatsService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class StatsController extends Controller{
	
	private final StatsService statsService;
	
	public StatsController(StatsService statsService) {
		this.statsService = statsService;
	}

	@Override
	public boolean supports(String route) {
		return route.startsWith("/stats");
	}

	@Override
	public Response handle(Request request) {
		if(request.getRoute().contains("/stats")) {
			switch(request.getMethod()) {
				case "GET": return stats(request);
			}
		}
		
		return status(HttpStatus.BAD_REQUEST);
	}

	private Response stats(Request request) {
		
    	String token = request.getAuthorization();
		Response response = new Response();
		
		String stats = statsService.stats(token);
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
