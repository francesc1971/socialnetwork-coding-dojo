/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Blueknow Labs
 *
 * (c) Copyright 2009-2019 Blueknow, S.L.
 *
 * ALL THE RIGHTS ARE RESERVED
 */
package com.blueknow.labs.network.springboot.adapter.out;

import java.util.Objects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blueknow.labs.network.model.User;
import com.blueknow.labs.network.port.out.UserRepository;
import com.blueknow.labs.network.springboot.entity.JpaUser;

@Repository
public interface JpaUserRepository extends UserRepository, JpaRepository<JpaUser, Long> {

	@Override
	default long persist(final User user) {
		final var jpaUser = this.save(JpaUser.wrap(Objects.requireNonNull(user, "User can not be null")));
		final long id = jpaUser.getId();
		user.setId(id);
		return id;
	}
	
	
	@Override
	@Query("from JpaUser where name = :name")
	User findUserByName(final String name);
	
}