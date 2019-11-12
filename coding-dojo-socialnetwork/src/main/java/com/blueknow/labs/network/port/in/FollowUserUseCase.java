/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Blueknow Labs
 *
 * (c) Copyright 2009-2019 Blueknow, S.L.
 *
 * ALL THE RIGHTS ARE RESERVED
 */
package com.blueknow.labs.network.port.in;

public interface FollowUserUseCase {

	/**
	 * @param name
	 * @param follower
	 * @return
	 */
	boolean follow(final String name, final String follower);
	
}