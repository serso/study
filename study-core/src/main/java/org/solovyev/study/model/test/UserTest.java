/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model.test;
/**
 * User: serso
 * Date: May 1, 2010
 * Time: 12:19:32 AM
 */

import junit.framework.*;
import org.solovyev.study.model.User;

import java.util.Date;

public class UserTest extends TestCase {
	User user;

	public void testToString() throws Exception {
		User user = new User();
		user.setUsername("username");
		user.setPassword(null);
		user.setEmail("email");
		user.setCreationDate(new Date());

		System.out.print(user);
	}
}