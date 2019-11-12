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

import java.util.Objects;
import java.util.Optional;

import com.blueknow.labs.network.model.User;
import com.blueknow.labs.network.port.in.AuthUserUseCase;
import com.blueknow.labs.network.port.in.ManageUserUseCase;
import com.blueknow.labs.network.port.in.FollowUserUseCase;
import com.blueknow.labs.network.port.out.UserRepository;

//Facade
public class UserService implements AuthUserUseCase, ManageUserUseCase, FollowUserUseCase {
	
	private final UserRepository repository;
	
	public UserService(final UserRepository repository) {
		this.repository = Objects.requireNonNull(repository, "UserRepository can not be null");
	}

	@Override
	public boolean existsUser(final String name) {
		Objects.requireNonNull(name, "Name can NOT be null");
		final var user = Optional.ofNullable(this.repository.findUserByName(name));
		return user.isPresent() && name.equals(user.get().getName());
	}
	
	public long save(final User user) {
		Objects.requireNonNull(user, "User can NOT be null");
		return this.repository.persist(user);
	}

	@Override
	public boolean follow(final String name, final String follower) {
		Objects.requireNonNull(name, "Name can NOT be null");
		final var user = Optional.ofNullable(this.repository.findUserByName(name));
		if (user.isEmpty()) {
			return false;
		}
		//it exists and we can add
		user.get().addFollower(new User(follower));
		return true;
	}

}