package com.blueknow.labs.network.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import com.blueknow.labs.network.model.User;
import com.blueknow.labs.network.port.in.UserUseCase;
import com.blueknow.labs.network.port.out.UserRepository;


public class UserServiceShould {
	
	private final UserRepository repository = new MockUserRepository();
	
	private UserUseCase service = new  UserService(this.repository);

	@Test
	void exist_user_with_given_name() {
		assertTrue(this.service.existsUser("lino"));
	}
	
	@Test
	void not_exist_user_with_given_name() {
		assertFalse(this.service.existsUser("francesc"));
	}
	
}
class MockUserRepository implements UserRepository {
	
	private final List<User> repository = List.of(new User("lino"));

	@Override
	public User findUserByName(final String name) {
		Objects.requireNonNull(name);
		return repository.stream().filter(user -> name.equals(user.getName())).findFirst().orElseGet(User::new);
	}

	@Override
	public long persist(final User user) {
		return 0;
	}
	
}