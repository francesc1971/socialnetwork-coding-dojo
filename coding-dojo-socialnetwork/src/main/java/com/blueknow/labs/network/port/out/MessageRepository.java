/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Blueknow Labs
 *
 * (c) Copyright 2009-2019 Blueknow, S.L.
 *
 * ALL THE RIGHTS ARE RESERVED
 */
package com.blueknow.labs.network.port.out;

import com.blueknow.labs.network.model.Message;

import java.util.List;

public interface MessageRepository {

    long persist(final Message message);

    List<Message> findMessagesByUser(final String user);
    
    /**
     * @param user
     * @param channel
     * @return
     */
    List<Message> findMessagesByUserAndChannel(final String user, final Message.Channel channel);
    
}