/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.solovyev.study.model.User;
import org.solovyev.study.model.UserSearchParams;
import org.solovyev.study.services.UserService;

import javax.sql.DataSource;

/**
 * User: serso
 * Date: Apr 1, 2010
 * Time: 11:15:31 PM
 */

@Service
public class StudyUserDetailsService implements UserDetailsService {

	private DataSource dataSource;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException, DataAccessException {
	    UserSearchParams userSearchParams = new UserSearchParams();
		userSearchParams.setUsername(s);
		UserDetails userDetails = UserService.loadUser(userSearchParams, dataSource );
		if (userDetails == null) {
			userDetails = new User();
		}
		return userDetails;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}