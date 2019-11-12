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

import java.io.Serializable;
import java.util.Objects;

public class Message implements Serializable {

    /**serialization stuff*/
	private static final long serialVersionUID = 1L;

	private long id;

    private String user;

    private String message;
    
    private Channel channel = Channel.TIMELINE;
    
    private Type type = Type.PUBLIC;

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

    public void setUser(final String user) {
        this.user = user;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Type getType() {
		return type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(final Channel channel) {
		this.channel = channel;
	}

	@Override
    public boolean equals(final Object o) {
        if (this == o) {
        	return true;
        }
        if (!(o instanceof Message)) {
        	return false;
        }
        final  Message message1 = (Message) o;
        return id == message1.id &&
                Objects.equals (user, message1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash (id, user, message, this.type, this.channel);
    }
    
	@Override
	public String toString() {
		return "Message [id=" + id + ", user=" + user + ", message=" + message + ", type=" + type + "]";
	}

	public enum Channel {
		TIMELINE, MENTIONS;
	}

	public enum Type {
    	PUBLIC, PRIVATE;
    }
    
}