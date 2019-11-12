/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Blueknow Labs
 *
 * (c) Copyright 2009-2019 Blueknow, S.L.
 *
 * ALL THE RIGHTS ARE RESERVED
 */
package com.blueknow.labs.network.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import com.blueknow.labs.network.model.User;
import com.blueknow.labs.network.port.out.UserRepository;


public class UserServiceShould {
	
	private final UserRepository repository = new MockUserRepository();
	
	private UserService service = new  UserService(this.repository);

	@Test
	void exist_user_with_given_name() {
		assertTrue(this.service.existsUser("lino"));
	}
	
	@Test
	void not_exist_user_with_given_name() {
		assertFalse(this.service.existsUser("francesc"));
	}
	
	@Test
	void create_user_from_scratch() {
		assertEquals(1L, this.service.save(new User("santi")));
	}
	
	@Test
	void add_follower_to_an_existent_user() {
		assertTrue(this.service.follow("lino", "fede"));
	}
	
}
class MockUserRepository implements UserRepository {
	
	private final List<User> repository = new ArrayList<>(List.of(new User("lino")));
	
	private final AtomicInteger generator = new AtomicInteger();

	@Override
	public User findUserByName(final String name) {
		Objects.requireNonNull(name);
		return repository.stream().filter(user -> name.equals(user.getName())).findFirst().orElseGet(User::new);
	}

	@Override
	public long persist(final User user) {
		user.setId(generator.incrementAndGet());
		this.repository.add(user);
		return user.getId();
	}
	
}