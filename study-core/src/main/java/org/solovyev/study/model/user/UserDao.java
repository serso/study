package org.solovyev.study.model.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 8/12/11
 * Time: 4:40 PM
 */
public interface UserDao {

	@Nullable
	User load(@NotNull String userName);

}
