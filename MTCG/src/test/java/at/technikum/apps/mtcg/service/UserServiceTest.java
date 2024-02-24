package at.technikum.apps.mtcg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalAnswers.*;

import org.junit.jupiter.api.Test;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.UserRepository;

class UserServiceTest {

	@Test
	void shouldSetUserId_whenSaveUser() {
		
		//Arrange
		UserRepository userRepository = mock(UserRepository.class);
		UserService userService = new UserService(userRepository);
		User user = new User("username", "pw");
		
		when(userRepository.save(any())).then(returnsFirstArg());
		
		//Act
		User answer = userService.save(user);
		
		//Assert
		assertNotEquals(null, answer.getId());
		assertNotEquals("", answer.getId());
		assertEquals("username", answer.getUsername());
		assertEquals("pw", answer.getPassword());
		assertEquals(20, answer.getCoin());
		assertEquals(100, answer.getEloValue());
		assertEquals(0, answer.getWin());
		assertEquals(0, answer.getLoss());
		assertEquals("", answer.getName());
		assertEquals("", answer.getBio());
		assertEquals(null, answer.getImage());
		assertEquals(null, answer.getToken());
	}
	
	@Test
	void shouldUpdateUser_whenUpdateUser() {
		
		//Arrange
		UserRepository userRepository = mock(UserRepository.class);
		UserService userService = new UserService(userRepository);
		
		User user = new User("", "", "", 0, 0, 0, 0, "", "", "", "");
		when(userRepository.get(any(), any())).thenReturn(user);
		when(userRepository.update(any())).then(returnsFirstArg());
		
		String changes = "{\"name\": \"Kienboeck\",  \"Bio\": \"me playin...\", \"Image\": \":-)\"}";
		
		//Act
		User answer = userService.update("", "", changes);
		
		//Assert
		assertEquals("Kienboeck", answer.getName());
		assertEquals("me playin...", answer.getBio());
		assertEquals(":-)", answer.getImage());
	}

}
