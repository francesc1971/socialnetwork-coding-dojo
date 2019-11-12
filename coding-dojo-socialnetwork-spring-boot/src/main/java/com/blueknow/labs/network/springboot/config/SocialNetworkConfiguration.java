/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Blueknow Labs
 *
 * (c) Copyright 2009-2019 Blueknow, S.L.
 *
 * ALL THE RIGHTS ARE RESERVED
 */
package com.blueknow.labs.network.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blueknow.labs.network.port.out.MessageRepository;
import com.blueknow.labs.network.port.out.UserRepository;
import com.blueknow.labs.network.service.MessageService;
import com.blueknow.labs.network.service.UserService;

@Configuration
public class SocialNetworkConfiguration {

	@Bean
	MessageService messageService(final MessageRepository repository) {
		return new MessageService(repository);
	}

	@Bean
	UserService userService(final UserRepository repository) {
		return new UserService(repository);
	}

}