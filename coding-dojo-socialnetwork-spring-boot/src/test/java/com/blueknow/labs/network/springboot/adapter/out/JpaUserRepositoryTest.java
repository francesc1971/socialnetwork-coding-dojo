package com.blueknow.labs.network.springboot.adapter.out;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.blueknow.labs.network.model.User;

@DataJpaTest
public class JpaUserRepositoryTest {

	@Autowired 
	private JpaUserRepository repository;
	
	@Test
	void should_save_simple_user() {
		final long id = this.repository.persist(new User("alice"));
		assertThat(id).isGreaterThan(0);
		final User user = this.repository.findUserByName("alice");
		assertThat(user).isNotNull();
		assertThat(user.getId()).isEqualTo(id);
		assertThat(user.getName()).isEqualTo("alice");
	}
	
	@Test
	void should_save_user_with_followers() {
		final long id = this.repository.persist(new User("alice"));
		assertThat(id).isGreaterThan(0);
		
		final User alice = this.repository.findUserByName("alice");
		assertThat(alice).isNotNull();
		assertThat(alice.getId()).isEqualTo(id);
		assertThat(alice.getName()).isEqualTo("alice");
		
		//add follower
		final User bob = new User("bob");
		alice.addFollower(bob);
		this.repository.persist(alice);//for to update and save new one
		
		
		final User aliceWithFollower = this.repository.findUserByName("alice");
		assertThat(aliceWithFollower).isNotNull();
		assertThat(aliceWithFollower.getFollowers()).isNotEmpty();
		final User persistentBob =  this.repository.findUserByName("bob");
		assertThat(aliceWithFollower.getFollowers()).containsAnyOf(persistentBob);
	}
	
	@Test
	void should_save_user_with_followers_from_scratch() {
		final User alice = new User("alice");
		alice.addFollower(new User("bob"));
		final long id = this.repository.persist(alice);
		
		final User persitentAlice = this.repository.findUserByName("alice");
		assertThat(persitentAlice).isNotNull();
		assertThat(persitentAlice.getId()).isEqualTo(id);
		assertThat(persitentAlice.getName()).isEqualTo("alice");
		final User persistentBob =  this.repository.findUserByName("bob");
		assertThat(persitentAlice.getFollowers()).containsAnyOf(persistentBob);
	}
	
}