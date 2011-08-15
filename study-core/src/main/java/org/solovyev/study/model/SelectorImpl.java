/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.html.Button;
import org.solovyev.common.utils.CollectionsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: Apr 24, 2010
 * Time: 1:47:06 PM
 */
public class SelectorImpl implements Selector {

	@NotNull
	private List<Integer> indexes = new ArrayList<Integer>();

	@NotNull
	private List<Button> buttons = new ArrayList<Button>();

	private boolean available = true;

	public SelectorImpl() {
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public boolean isMultiSelectAvailable() {
		return true;
	}

	@NotNull
	@Override
	public List<Integer> getIndexes() {
		return indexes;
	}

	@Override
	public void setIndexes(@Nullable List<Integer> indexes) {
		this.indexes = (List<Integer>)CollectionsUtils.setNotNull(indexes, this.indexes);
	}

	@NotNull
	@Override
	public List<Button> getButtons() {
		return buttons;
	}

	public void setButtons(@NotNull List<Button> buttons) {
		this.buttons = buttons;
	}

	@NotNull
	public <T> List<T> getSelectedItems(@NotNull List<T> items) {
		final List<T> result = new ArrayList<T>();

		for (Integer index : indexes) {
			if (index >= 0 && index < items.size()) {
				result.add(items.get(index));
			}
		}

		return result;
	}

	public void clear() {
		getIndexes().clear();
	}
}
