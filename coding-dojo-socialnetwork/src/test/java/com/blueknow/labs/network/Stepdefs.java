package com.blueknow.labs.network;

import static org.junit.jupiter.api.Assertions.*;

import com.blueknow.labs.network.port.in.PublishMessageUseCase;
import com.blueknow.labs.network.service.PublishMessageService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefs {

    private PublishMessageUseCase publishMessageUseCase = new PublishMessageService (new MapMessageRepository ());

    private String user;

    private String message;

    @Given ("Alice writes in console Hello")
    public void alice_writes_in_console_hello() {
        this.user = "Alice";
        this.message = "Hello";
    }

    @When ("Alice sends Hello to server and it is published")
    public void alice_sends_hello_and_it_is_published() {
       this.publishMessageUseCase.publish (this.user, this.message);
    }

    @Then ("Server sends to Alice Hello to her timeline")
    public void server_sends_to_Alice_hello_to_her_timeline() {
        final var messages = this.publishMessageUseCase.findMessagesByUser (this.user);
        assertNotNull (messages);
        assertFalse (messages.isEmpty ());
        assertTrue (messages.contains (this.message));
    }

}
