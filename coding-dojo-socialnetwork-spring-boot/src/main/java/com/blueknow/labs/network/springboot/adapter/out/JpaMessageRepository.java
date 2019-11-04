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

import com.blueknow.labs.network.model.Message;
import com.blueknow.labs.network.port.out.MessageRepository;
import com.blueknow.labs.network.springboot.entity.JpaMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaMessageRepository extends MessageRepository, JpaRepository<JpaMessage, Long> {

    @Override
    default long persist(final Message message) {
        final var persisted = this.save (new JpaMessage (message));
        return persisted.getId ();
    }

    @Override
    @Query("from JpaMessage where user = :user")
    List<Message> findMessagesByUser(final String user);

}