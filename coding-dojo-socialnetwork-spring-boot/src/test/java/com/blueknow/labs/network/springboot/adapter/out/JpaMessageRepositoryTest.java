/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Blueknow Labs
 *
 * (c) Copyright 2009-2019 Blueknow, S.L.
 *
 * ALL THE RIGHTS ARE RESERVED
 */
package com.blueknow.labs.network.springboot.adapter.out;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.blueknow.labs.network.model.Message;
import com.blueknow.labs.network.model.Message.Channel;
import com.blueknow.labs.network.model.Message.Type;
import com.blueknow.labs.network.port.out.MessageRepository;

@DataJpaTest
public class JpaMessageRepositoryTest {

	@Autowired
	private MessageRepository repository;
	
	@Test
	void should_persist_message() {
		final var id = this.repository.persist(new Message("linobot", "hello"));
		
		assertThat(id).isGreaterThan(0);
	}
	
	@Test
	void should_exists_message_for_a_given_user() {
		assertThat(this.repository.persist(new Message("linobot+2", "hello 1"))).isGreaterThan(0);
		assertThat(this.repository.persist(new Message("linobot+2", "hello 2"))).isGreaterThan(0);
		assertThat(this.repository.persist(new Message("linobot+2", "hello 3"))).isGreaterThan(0);
		
		final var messages = this.repository.findMessagesByUser("linobot+2");
		
		assertThat(messages).isNotEmpty();
		assertThat(messages).hasSize(3);
	}
	
	@Test
	void should_exists_message_for_a_given_channel() {
		//Timeline
		assertThat(this.repository.persist(new Message("linobot+3", "hello 1"))).isGreaterThan(0);
		assertThat(this.repository.persist(new Message("linobot+3", "hello 11"))).isGreaterThan(0);
		assertThat(this.repository.persist(new Message("linobot+3", "hello 111"))).isGreaterThan(0);
		//Mentions
		final Message msg = new Message("linobot+3", "Mentions message @linobo+3");
		msg.setChannel(Channel.MENTIONS);
		msg.setType(Type.PUBLIC);
		assertThat(this.repository.persist(msg)).isGreaterThan(0);
		
		final var timelineMessages = this.repository.findMessagesByUserAndChannel("linobot+3", Channel.TIMELINE);
		
		assertThat(timelineMessages).isNotEmpty();
		assertThat(timelineMessages).hasSize(3);
		
		final var mentionsMessages = this.repository.findMessagesByUserAndChannel("linobot+3", Channel.MENTIONS);
		
		assertThat(mentionsMessages).isNotEmpty();
		assertThat(mentionsMessages).hasSize(1);
		
		//all messages
		final var messages = this.repository.findMessagesByUser("linobot+3");
		
		assertThat(messages).isNotEmpty();
		assertThat(messages).hasSize(4);
		
	}
	
}