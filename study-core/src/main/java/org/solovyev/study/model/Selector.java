/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.html.Button;

import java.util.List;

/**
 * User: serso
 * Date: Apr 24, 2010
 * Time: 1:44:51 PM
 */
public interface Selector {

	public boolean isAvailable();

	public boolean isMultiSelectAvailable();

	@NotNull
	public List<Integer> getIndexes();

	public void setIndexes( @Nullable List<Integer> indexes );

	public List<Button> getButtons();

	public <T> List<T> getSelectedItems ( List<T> items );
}
