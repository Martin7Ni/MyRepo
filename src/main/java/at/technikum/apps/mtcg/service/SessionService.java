package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.UserRepository;

public class SessionService {
	
	private final UserRepository userRepository;
	
	public SessionService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public String login(User user) {
		return userRepository.login(user);
	}
}
