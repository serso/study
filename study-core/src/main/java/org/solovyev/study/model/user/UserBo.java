package org.solovyev.study.model.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 8/12/11
 * Time: 4:42 PM
 */
public interface UserBo {

		@Nullable
		User load(@NotNull String userName);
}
