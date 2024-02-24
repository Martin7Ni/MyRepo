package at.technikum.apps.mtcg.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.service.PackageService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import at.technikum.server.util.HttpMapper;

public class PackageController extends Controller {
	
	private final PackageService packageService;
	
	public PackageController(PackageService packageService) {
		this.packageService = packageService;
	}

	@Override
	public boolean supports(String route) {
		return route.startsWith("/packages");
	}

	@Override
	public Response handle(Request request) {
		HttpMapper httpMapper = new HttpMapper();
		
		if(request.getRoute().equals("/packages")) {
			if(!request.getAuthorization().equals("Bearer admin-mtcgToken")) {
				System.out.println("Not authorised");
			}
			switch (request.getMethod()) {
				case "POST": return create(request);
			}
			
		}
		return status(HttpStatus.BAD_REQUEST);
	}

	public Response create(Request request) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<Card> packages = null;
		try {
			packages = objectMapper.readValue((request.getBody()), new TypeReference<List<Card>>() {});
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		
		packages = packageService.save(packages);
		
		String packageJson = null;
		try {
			packageJson = objectMapper.writeValueAsString(packages);
		} catch (JsonProcessingException e) {
			return status(HttpStatus.BAD_REQUEST);
		}
		
		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setContentType(HttpContentType.APPLICATION_JSON);
		response.setBody(packageJson);
		return response;
	}
}
