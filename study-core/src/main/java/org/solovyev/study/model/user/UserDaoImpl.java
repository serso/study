package org.solovyev.study.model.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.study.model.CustomHibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * User: serso
 * Date: 8/12/11
 * Time: 4:41 PM
 */


@Repository("userDao")
public class UserDaoImpl extends CustomHibernateDaoSupport implements UserDao {

	@Nullable
	@Override
	public User load(@NotNull String userName) {
		return (User)getHibernateTemplate().find("from users as u where u.username = ? ", userName);
	}
}
