/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Blueknow Labs
 *
 * (c) Copyright 2009-2019 Blueknow, S.L.
 *
 * ALL THE RIGHTS ARE RESERVED
 */
package com.blueknow.labs.network.service;

import com.blueknow.labs.network.port.out.MessageRepository;
import com.blueknow.labs.network.port.in.PublishMessageUseCase;
import com.blueknow.labs.network.port.in.ReadMessageUseCase;
import com.blueknow.labs.network.model.Message;
import com.blueknow.labs.network.model.Message.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MessageService implements PublishMessageUseCase, ReadMessageUseCase {

    private static final Logger log = LoggerFactory.getLogger (MessageService.class);

    private final MessageRepository repository;

    public MessageService(final MessageRepository repository) {
        this.repository = Objects.requireNonNull (repository);
    }

    @Override
    public void publish(final String user, final String message) {
        Objects.requireNonNull (user, "User can NOT be null");
        Objects.requireNonNull (message, "Message can NOT be null");
        log.info ("Saving Message [{}] of user {} ", message, user);
        this.repository.persist (new Message (user, message));
    }

    @Override
    public List<String> findMessagesByUser(final String user) {
        Objects.requireNonNull (user, "User can NOT be null");
        log.info ("Finding messages for user {}", user);
        final var messages = this.repository.findMessagesByUser (user);
        log.info ("Found messages {}", messages);
        return messages.stream ().map (message -> message.getMessage ()).collect(Collectors.toList());
    }
    
    @Override
    public List<String> findMessagesByUserAndChannelTimeline(final String user) {
    	Objects.requireNonNull (user, "User can NOT be null");
    	final var messages = this.repository.findMessagesByUserAndChannel(user, Channel.TIMELINE);
    	
    	return messages.stream ().map (message -> message.getMessage ()).collect(Collectors.toList());
    }

}