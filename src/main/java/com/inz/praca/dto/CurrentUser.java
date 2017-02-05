package com.inz.praca.dto;

import com.inz.praca.domain.User;
import lombok.EqualsAndHashCode;

import org.springframework.security.core.authority.AuthorityUtils;

@EqualsAndHashCode(callSuper = true)
public class CurrentUser extends org.springframework.security.core.userdetails.User {

	transient User user;

	public CurrentUser(User user) {
		super(user.getEmail(), user.getPasswordHash(), true, true, true, true, AuthorityUtils.createAuthorityList("ADMIN"));
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public Long getId() {
		return user.getId();
	}

}