package com.blueknow.labs.network.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import com.blueknow.labs.network.model.Message;
import com.blueknow.labs.network.model.Message.Channel;
import com.blueknow.labs.network.port.out.MessageRepository;

public class MessageServiceShould {
	
	final MessageRepository repository = new MockMessageRepository();
	
	final MessageService service = new MessageService(this.repository);

	@Test
	void persist_message_and_retrieve_from_repository() {
		this.service.publish("lino", "hello");
		
		final List<Message> messages = this.repository.findMessagesByUser("lino");
		assertFalse(messages.isEmpty());
		assertEquals(1, messages.size());
		assertEquals("lino", messages.iterator().next().getUser());
	}
	
}
class MockMessageRepository implements MessageRepository {
	
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