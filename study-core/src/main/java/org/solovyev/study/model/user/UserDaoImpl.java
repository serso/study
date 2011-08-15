package org.solovyev.study.model.user;

import org.jetbrains.annotations.NotNull;
import org.solovyev.study.model.CustomHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: serso
 * Date: 8/12/11
 * Time: 4:41 PM
 */


@Repository("userDao")
public class UserDaoImpl extends CustomHibernateDaoSupport implements UserDao {

	@NotNull
	@Override
	public List<User> load(@NotNull String userName) {
		return (List<User>)getHibernateTemplate().find("from user as u where u.username = ? ", userName);
	}
}
