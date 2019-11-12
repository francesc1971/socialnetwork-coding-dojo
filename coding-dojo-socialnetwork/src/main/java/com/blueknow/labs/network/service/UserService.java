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

import com.blueknow.labs.network.model.User;
import com.blueknow.labs.network.port.in.UserUseCase;
import com.blueknow.labs.network.port.out.UserRepository;

public class UserService implements UserUseCase {
	
	private final UserRepository repository;
	
	public UserService(final UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean existsUser(final String name) {
		Objects.requireNonNull(name, "Name can NOT be null");
		final User user = this.repository.findUserByName(name);
		return user != null && name.equals(user.getName());
	}

}