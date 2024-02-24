package at.technikum.apps.mtcg.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.UserRepository;

public class UserService {

	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User save(User user) {
		user.setId(UUID.randomUUID().toString());
		return userRepository.save(user);
	}

	public User get(String username, String token) {
		return userRepository.get(username, token);
	}
	
	public User update(String username, String token, String changes) {
		User user = userRepository.get(username, token);
		if(user == null)return null;
		
		user.setName(changes.split("\"")[3]);
		user.setBio(changes.split("\"")[7]);
		user.setImage(changes.split("\"")[11]);
		return userRepository.update(user);
	}
}
