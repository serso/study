package org.solovyev.study.model.user;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * User: serso
 * Date: 8/12/11
 * Time: 4:42 PM
 */
public interface UserBo {

		@NotNull
		List<User> load(@NotNull String userName);
}
