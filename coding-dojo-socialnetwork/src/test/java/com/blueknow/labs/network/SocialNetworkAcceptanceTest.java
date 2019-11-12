package com.blueknow.labs.network;

import static org.junit.jupiter.api.Assertions.*;

import com.blueknow.labs.network.port.out.MessageRepository;
import com.blueknow.labs.network.port.in.PublishMessageUseCase;
import com.blueknow.labs.network.model.Message;
import com.blueknow.labs.network.model.Message.Channel;
import com.blueknow.labs.network.service.PublishMessageService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SocialNetworkAcceptanceTest {

    private static final Logger logger = LoggerFactory.getLogger (SocialNetworkAcceptanceTest.class);

    private final PublishMessageUseCase publishMessageUseCase = new PublishMessageService (new MapMessageRepository ());
    /**Thread Safe*/
    //HttpClient instance is immutable, thus automatically thread-safe, and you can send multiple requests with it.
    private final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30)).build();

    private final Gson gson = new Gson ();

    private final Type listType = new TypeToken<List<String>> (){}.getType ();

    private HttpServer server;

   @BeforeEach
   void init () {
       try {
           this.server =   HttpServer.create (new InetSocketAddress (8080), 0);
           this.server.createContext ("/api/publish/", httpExchange -> {
               logger.info ("Request /api/publish/***");
               //handle the request
               final var uri = httpExchange.getRequestURI ();
               final var elements = uri.getPath ().split ("/");
               final var user = elements[elements.length-1];
               final var message = new String(httpExchange.getRequestBody ().readAllBytes (), StandardCharsets.UTF_8);
               //delegate to the UseCase
               this.publishMessageUseCase.publish(user, message);
               //write the response
               final var respBody = "OK".getBytes(StandardCharsets.UTF_8);
               httpExchange.getResponseHeaders().put("Content-Type", Collections.singletonList("text/plain; charset=UTF-8"));
               httpExchange.sendResponseHeaders(200, respBody.length);
               httpExchange.getResponseBody().write(respBody);
               httpExchange.getResponseBody().close();

           });
           this.server.createContext ("/api/messages/", httpExchange -> {
               //handle the request
               logger.info ("Request /api/messages/***");
               final var uri = httpExchange.getRequestURI ();
               final var elements = uri.getPath ().split ("/");
               final var user = elements[elements.length-1];
               //
               final var messages = this.publishMessageUseCase.findMessagesByUser (user);
               logger.info ("List of  messages : {}", messages);
               final var json = this.gson.toJson (messages, this.listType);
               //it could be a JSON or whatever
               final var respBody = json.getBytes (StandardCharsets.UTF_8);
               httpExchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
               httpExchange.sendResponseHeaders(200, respBody.length);
               httpExchange.getResponseBody().write(respBody);
               httpExchange.getResponseBody().close();

           });
           //we could pass a null and a default one is selected, for sake of simplicity we select and easy one
           this.server.setExecutor (Executors.newFixedThreadPool (2));
           this.server.start ();
       } catch (final IOException e) {
           throw new IllegalStateException (e);
       }
   }

   @AfterEach
   void destroy() {
       if (this.server != null) {
           this.server.stop (1);
       }
   }

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
    void should_alice_publish_a_message_to_personal_time_line() throws IOException, InterruptedException {
        final var requestSendMessage = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/publish/alice"))
                .POST (HttpRequest.BodyPublishers.ofString ("A Hello Message"))
                .build();
        //Send the message and expect that it is OK
        final var httpResponse = this.client.send (requestSendMessage, HttpResponse.BodyHandlers.ofString ());
        assertEquals ("OK", httpResponse.body ());
        assertEquals (200, httpResponse.statusCode ());

        final var requestMessages = HttpRequest.newBuilder (URI.create ("http://localhost:8080/api/messages/alice")).GET ().build ();
        //Request Messages, the Body is a JSON with the list of messages and we must ensure that our Initial message is expected to be in the timeline
        final var httpResponseMessages = this.client.send (requestMessages, HttpResponse.BodyHandlers.ofString ());
        assertEquals (200, httpResponse.statusCode ());
        final var body = httpResponseMessages.body ();
        final var messages = this.gson.fromJson (body, List.class);
        assertNotNull(messages);
        assertFalse (messages.isEmpty ());
        assertTrue (messages.contains ("A Hello Message"));
    }

}
class MapMessageRepository implements MessageRepository {

    private final Map<String, List<Message>> messages = new HashMap<> ();

    private final AtomicInteger idGenerator = new AtomicInteger (1);

    @Override
    public long persist(final Message message) {
        this.messages.compute (message.getUser (), (s, listOfMessages) -> {
            final var messageList = Optional.ofNullable (listOfMessages).orElseGet (ArrayList::new);
            message.setId (idGenerator.getAndIncrement ());
            messageList.add (message);
            return messageList;
        });
        return message.getId ();
    }

    @Override
    public List<Message> findMessagesByUser(final String user) {
        return Optional.ofNullable (this.messages.get (user)).orElseGet (ArrayList::new);
    }

	@Override
	public List<Message> findMessagesByUserAndChannel(final String user, final Channel channel) {
		return this.findMessagesByUser(user);
	}

}