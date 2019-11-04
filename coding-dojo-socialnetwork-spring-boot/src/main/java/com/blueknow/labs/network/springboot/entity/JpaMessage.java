/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Blueknow Labs
 *
 * (c) Copyright 2009-2019 Blueknow, S.L.
 *
 * ALL THE RIGHTS ARE RESERVED
 */
package com.blueknow.labs.network.springboot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.blueknow.labs.network.model.Message;

@Entity
@Table(name = "message")
public class JpaMessage extends Message implements Serializable {

    /**serialization stuff */
	private static final long serialVersionUID = 1L;

	/**default constructor*/
	public JpaMessage() {
    }

    /**copy constructor*/
    public JpaMessage(final Message message) {
        this.setMessage (message.getMessage ());
        this.setUser (message.getUser ());
    }

    @Override
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    public long getId() {
        return super.getId ();
    }

    @Override
    @Column
    public String getMessage() {
        return super.getMessage ();
    }

    @Override
    @Column
    public String getUser() {
        return super.getUser ();
    }
    
}