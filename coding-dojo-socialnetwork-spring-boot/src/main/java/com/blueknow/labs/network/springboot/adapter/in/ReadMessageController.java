/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Blueknow Labs
 *
 * (c) Copyright 2009-2019 Blueknow, S.L.
 *
 * ALL THE RIGHTS ARE RESERVED
 */
package com.blueknow.labs.network.springboot.adapter.in;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.blueknow.labs.network.port.in.ReadMessageUseCase;

import reactor.core.publisher.Flux;

@RestController
public class ReadMessageController {
	
	private final ReadMessageUseCase useCase;
	
	public ReadMessageController(final ReadMessageUseCase useCase) {
		this.useCase = useCase;
	}
	
	@GetMapping(path = "/api/read/{user}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<String> readMessagesFromUser(@PathVariable("user") final String user) {
		return Flux.defer(() -> Flux.fromIterable(useCase.findMessagesByUserAndChannelTimeline(user))).log();
	}

}