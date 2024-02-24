package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.repository.UserRepository;

public class ScoreboardService {

	private final UserRepository userRepository;
	
	public ScoreboardService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public String scoreboard(String token) {
		return userRepository.scoreboard(token);
	}
}
