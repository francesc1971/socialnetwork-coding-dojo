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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    /**serialization stuff*/
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String name;
	
	private User parent;
	
	private List<? super User> followers = new ArrayList<>();
	
	public User() {
	}
	
	public User(final String name) {
		this.name = name;
	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public List<? super User> getFollowers() {
		return followers;
	}

	public void setFollowers(final List<? super User> followers) {
		this.followers = Objects.requireNonNull(followers);
	}

	public User getParent() {
		return parent;
	}

	public void setParent(User parent) {
		this.parent = parent;
	}
	
	@Override
	public boolean equals(final Object o) {
		 if (this == o) {
        	return true;
        }
        if (!(o instanceof User)) {
        	return false;
        }
        final  User user1 = (User) o;
        return id == user1.id &&
                Objects.equals (this.name, user1.name);
	}
	
	@Override
	public int hashCode() {
		 return Objects.hash (id, name);
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", parent=" + parent + "]";
	}

	public void addFollower(final User follower) {
		Objects.requireNonNull(follower);
		follower.setParent(this);
		this.getFollowers().add(follower);
	}
}