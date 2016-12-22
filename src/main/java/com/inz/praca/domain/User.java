package com.inz.praca.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

@Entity
@Getter
@ToString
@Slf4j
@Setter
@NoArgsConstructor(force = true)
public class User {
	@Column(unique = true)
	private final String email;
	private final String name;
	private final String lastName;
	private final String passwordHash;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ORDER")
	private List<Order> shippingDetails;

	@Id
	@GeneratedValue
	private Long id;

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

	public void setId(long id) {
		this.id = id;
	}
}
