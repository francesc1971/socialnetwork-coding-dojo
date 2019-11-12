package com.blueknow.labs.network.port.out;

import com.blueknow.labs.network.model.User;

public interface UserRepository {
	
	
	long persist(final User user);

	/**
	 * @param name
	 * @return
	 */
	User findUserByName(final String name);
	
}