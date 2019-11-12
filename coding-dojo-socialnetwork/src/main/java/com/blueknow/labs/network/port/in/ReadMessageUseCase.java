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

import java.util.List;

public interface ReadMessageUseCase {

	/**
	 * @param user
	 * @return
	 */
	 List<String> findMessagesByUserAndChannelTimeline(final String user);
	 
}