/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Blueknow Labs
 *
 * (c) Copyright 2009-2019 Blueknow, S.L.
 *
 * ALL THE RIGHTS ARE RESERVED
 */
package com.blueknow.labs.network.springboot;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialNetworkAcceptanceTest {

    static final Logger log = LoggerFactory.getLogger (SocialNetworkAcceptanceTest.class);

    // Spring Boot will create a `WebTestClient` for you,
    // already configure and ready to issue requests against "localhost:RANDOM_PORT"
    @Autowired
    private WebTestClient client;

    //[Use case] Posting: Alice can publish messages to a personal timeline
    /*
     * Use Case steps :
     *
     * 1.- Alice writes something and send to a given server
     * 2.- Server saves the message into a Repository for the given user Alice (Message entity : user, List<Messages>)
     * 3.- It sends a OK Http Status to client
     * 4.- Client requests for all the messages from Alice and expects the message previously published
     */
    @Test
    void should_alice_publish_a_message_to_personal_time_line() {
    	//Alice publish something in the timeline
        final Mono<String> response = this.client.post ().uri ("/api/publish/alice")
                .bodyValue ("A simple Hello Message").exchange ()
                .expectStatus().isOk()
                .returnResult(String.class).getResponseBody().single();
        response.subscribe (str -> assertEquals ("OK", str));
        //
        final Flux<String> results = this.client.get ().uri ("/api/publish/alice").exchange ()
                .expectStatus ().isOk ()
                .returnResult (String.class).getResponseBody();
        results.subscribe (values -> {
            log.info ("[should_alice_publish_a_message_to_personal_time_line] Results :: "+values);
            assertThat(values).contains("A simple Hello Message");
        });
    }
    
    //[Use case] Reading: Bob can view Alice’s timeline
    /*
     * 1.- Alice writes something and send to a given server
     * 2.- Server saves the message into a Repository for the given user Alice (Message entity : user, List<Messages>)
     * 3.- It sends a OK Http Status to client
     * 4.- Bob requests the public messages from Alice
     */
    @Test
    void should_bob_view_alice_public_messages() throws Exception {
    	//Alice publish something in the timeline
    	final Mono<String> response = this.client.post ().uri ("/api/publish/alice")
                 .bodyValue ("A Hello Message to be read").exchange ()
                 .expectStatus().isOk()
                 .returnResult(String.class).getResponseBody().single();
        response.subscribe (str -> assertEquals ("OK", str));
        //Bob or wahtever can view without any problems
    	final Flux<String> results = this.client.get ().uri ("/api/read/alice")
    			.header("Authorization", "Bearer bob+383duesnsjyndhehsb")
    			.exchange ()
                .expectStatus ()
                .isOk ()
                .returnResult (String.class).getResponseBody();
        results.subscribe (values -> {
            log.info ("[should_bob_view_alice_public_messages] Results :: "+values);
            assertThat(values).contains("A Hello Message to be read");
        });
        TimeUnit.MILLISECONDS.sleep(100);
    }
    
}