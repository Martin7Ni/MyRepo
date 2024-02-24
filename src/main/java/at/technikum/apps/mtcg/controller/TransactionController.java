package at.technikum.apps.mtcg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import at.technikum.apps.mtcg.service.TransactionService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class TransactionController extends Controller{
	
	private final TransactionService transactionService;
	
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@Override
	public boolean supports(String route) {
		return route.startsWith("/transactions/packages");
	}

	@Override
	public Response handle(Request request) {
		
		switch (request.getMethod()) {
			case "POST": return acquire(request);
		}
		
		return status(HttpStatus.BAD_REQUEST);
	}

	public Response acquire(Request request) {
		
		Response response = new Response();
		if(transactionService.acquire(request.getAuthorization())) {
			response.setStatus(HttpStatus.OK);
	        response.setContentType(HttpContentType.APPLICATION_JSON);
	        response.setBody("Acquired!");
		} else {
			response.setStatus(HttpStatus.NOT_FOUND);
	        response.setContentType(HttpContentType.APPLICATION_JSON);
	        response.setBody("Something went wrong!");
		}
		
		return response;
	}
}
