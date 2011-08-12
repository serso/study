/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.security;

import org.jetbrains.annotations.NotNull;
import org.solovyev.study.model.user.User;
import org.solovyev.study.model.user.UserBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * User: serso
 * Date: Apr 1, 2010
 * Time: 11:15:31 PM
 */

@Service
public class StudyUserDetailsService implements UserDetailsService {

	@Autowired
	@NotNull
	private UserBo userBo;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException, DataAccessException {
		UserDetails userDetails = userBo.load(s);
		if (userDetails == null) {
			userDetails = new User();
		}
		return userDetails;
	}

	public void setUserBo(@NotNull UserBo userBo) {
		this.userBo = userBo;
	}
}