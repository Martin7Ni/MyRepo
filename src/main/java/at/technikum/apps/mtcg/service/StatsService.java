package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.repository.UserRepository;

public class StatsService {

	private final UserRepository userRepository;
	
	public StatsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public String stats(String token) {
		return this.userRepository.getStats(token);
	}
}
