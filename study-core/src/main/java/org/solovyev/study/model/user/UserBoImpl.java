package org.solovyev.study.model.user;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: serso
 * Date: 8/12/11
 * Time: 4:42 PM
 */

@Service("userBo")
public class UserBoImpl implements UserBo {

	@Autowired
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@NotNull
	@Override
	public List<User> load(@NotNull String userName) {
		return userDao.load(userName);
	}
}
