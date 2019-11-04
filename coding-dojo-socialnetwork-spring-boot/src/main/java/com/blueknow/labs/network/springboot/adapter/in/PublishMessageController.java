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

import com.blueknow.labs.network.port.in.PublishMessageUseCase;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PublishMessageController {

    private final PublishMessageUseCase publishMessageUseCase;

    public PublishMessageController(final PublishMessageUseCase publishMessageUseCase) {
        this.publishMessageUseCase = publishMessageUseCase;
    }

    @PostMapping ("/api/publish/{user}")
    public Mono<String> save(@PathVariable("user") final String user, @RequestBody final String message) {
        return Mono.fromSupplier (() -> {
            //publish
            this.publishMessageUseCase.publish (user, message);
            return "OK";
        });
    }

	@GetMapping(path ="/api/messages/{user}",  produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<String> findMessages(@PathVariable("user") final String user) {
		return Flux.defer(() -> Flux.fromIterable(this.publishMessageUseCase.findMessagesByUser(user))).log();

	}
    
}