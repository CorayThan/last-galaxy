package com.corinthgames.lastgalaxy.models;

import com.corinthgames.lastgalaxy.enums.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.nathanwestlake.dartgenerator.annotations.DartInclude;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@DartInclude
@Entity(name="user_table")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="@id")
public class User {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(unique=true)
	@NotNull
	private String email;

	@Column(unique=true)
	@NotNull
	private String username;
	@NotNull
	//TODO remove from return jsons
	private String password;
	@Enumerated(EnumType.STRING)
	@NotNull
	private Role role;

	@OneToOne
	private House house;

	@Override
	public String toString() {
		return "User{" +
				"email='" + email + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", role=" + role +
				", house=" + house +
				'}';
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

}