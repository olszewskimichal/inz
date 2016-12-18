package com.inz.praca.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

@Entity
@Getter
@ToString
@Slf4j
public class User {
	@Column(unique = true)
	private final String email;
	private final String name;
	private final String lastName;
	private final String passwordHash;
	@Id
	@GeneratedValue
	private Long id;

	protected User() {
		this.email = null;
		this.name = null;
		this.lastName = null;
		this.passwordHash = null;
		log.debug("Pusty konstuktor dla hibernate, chce wiedzieć kiedy jest wykonywany {}", this);
	}

	public User(String email, String name, String lastName, String passwordHash) {
		Assert.hasLength(passwordHash, "Nie może być puste hasło");
		Assert.hasLength(name, "Imie nie może być puste");
		Assert.hasLength(lastName, "Nazwisko nie może być puste");
		Assert.hasLength(email, "Email nie moze być pusty"); //TODO zrobic asercje maila
		this.email = email;
		this.name = name;
		this.lastName = lastName;
		this.passwordHash = passwordHash;
	}

}
