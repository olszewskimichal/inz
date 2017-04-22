package com.inz.praca.registration;

import lombok.EqualsAndHashCode;

import org.springframework.security.core.authority.AuthorityUtils;

@EqualsAndHashCode(callSuper = true)
public class CurrentUser extends org.springframework.security.core.userdetails.User {

	transient User user;

	public CurrentUser(User user) {
		super(user.getEmail(), user.getPasswordHash(), user.isActivated(), true, true, true, AuthorityUtils.createAuthorityList(user.getRole().name()));
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}