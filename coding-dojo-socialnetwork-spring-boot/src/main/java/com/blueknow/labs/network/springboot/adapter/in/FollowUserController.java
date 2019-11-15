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

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueknow.labs.network.port.in.FollowUserUseCase;

import reactor.core.publisher.Mono;

@RestController
public class FollowUserController {

	/***/
	private final FollowUserUseCase followUserUseCase;
	
	public FollowUserController(final FollowUserUseCase followUserUseCase) {
		this.followUserUseCase = followUserUseCase;
	}
	
	@PostMapping ("/api/follow/{user}/{follower}")
    public Mono<String> follow(@PathVariable("user") final String name, @PathVariable("follower") final String follower) {
        return Mono.fromSupplier (() -> {
            //follow
            this.followUserUseCase.follow(name, follower);
            return "OK";
        });
    }

}