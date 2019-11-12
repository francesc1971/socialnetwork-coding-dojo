package com.blueknow.labs.network.springboot.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.blueknow.labs.network.model.User;

@Entity
@Table(name = "user")
public class JpaUser extends User implements Serializable {

	/**serialization stuff */
	private static final long serialVersionUID = 1L;

	
	public JpaUser() {
	}
	
	/**copy constructor */
	JpaUser(final User user) {
		if (user instanceof JpaUser) {
			throw new IllegalStateException();
		}
		this.setName(user.getName());
		//followers
		//1.- parent
		if (user.getParent() != null) {
			//TODO how to deal with this case? necessary?
		}
		//2.- followers
		if (!user.getFollowers().isEmpty()) {
			user.getFollowers().forEach(follower -> {
				//
				final var ufollower = User.class.cast(follower);
				final var jpaFollower = new  JpaUser();
				jpaFollower.setName(ufollower.getName());
				this.addFollower(jpaFollower);
			});
		}
	}
	
	@Override
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public long getId() {
		return super.getId();
	}
	
	@Override
	@Column
	public String getName() {
		return super.getName();
	}
	
	@OneToMany(mappedBy = "parent", targetEntity = JpaUser.class, cascade = CascadeType.ALL)
	@Override
	public List<? super User> getFollowers() {
		return super.getFollowers();
	}

	@ManyToOne(targetEntity = JpaUser.class)
	@Override
	public User getParent() {
		return super.getParent();
	}
	
	@Override
	public void addFollower(User follower) {
		super.addFollower(wrap(follower));
	}
	
	public static JpaUser wrap(final User user) {
		return user instanceof JpaUser? JpaUser.class.cast(user): new JpaUser(user);
	}
	
}