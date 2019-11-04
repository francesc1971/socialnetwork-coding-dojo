package com.blueknow.labs.network.springboot;

import static org.junit.jupiter.api.Assertions.*;

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

    //Use case : Alice can publish messages to a personal timeline
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
        //
        final Mono<String> response = this.client.post ().uri ("/api/publish/alice")
                .bodyValue ("A Hello Message").exchange ()
                .expectStatus().isOk()
                .returnResult(String.class).getResponseBody().single();
        response.subscribe (str -> assertEquals ("OK", str));
        //
        final Flux<String> results = this.client.get ().uri ("/api/messages/alice").exchange ()
                .expectStatus ().isOk ()
                .returnResult (String.class).getResponseBody();
        results.subscribe (values -> {
            log.info ("Results :: "+values);
            assertEquals ("A Hello Message", values);
        });
    }
    
}