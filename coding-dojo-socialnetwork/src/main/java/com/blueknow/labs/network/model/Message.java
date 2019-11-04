/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Blueknow Labs
 *
 * (c) Copyright 2009-2019 Blueknow, S.L.
 *
 * ALL THE RIGHTS ARE RESERVED
 */
package com.blueknow.labs.network.model;

import java.util.Objects;

public class Message {

    private long id;

    private String user;

    private String message;

    public Message() {

    }

    public Message(final String user, final String message) {
        this.user = user;
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public long getId() {
        return id;
    }

    public final void setId(long id) {
        this.id = id;
    }

    public final void setUser(final String user) {
        this.user = user;
    }

    public final void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message1 = (Message) o;
        return id == message1.id &&
                Objects.equals (user, message1.user) &&
                Objects.equals (message, message1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash (id, user, message);
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
    
}