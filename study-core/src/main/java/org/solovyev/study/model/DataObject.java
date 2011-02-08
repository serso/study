/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model;

import org.jetbrains.annotations.Nullable;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;
import org.solovyev.common.definitions.Identity;

import java.util.Date;

/**
 * User: serso
 * Date: Apr 11, 2010
 * Time: 5:29:12 PM
 */
public class DataObject<T> extends Identity<T> {

	private Date creationDate;

	private Integer creatorId;

	private Date modificationDate;

	public DataObject(T id) {
		super(id);
	}

	@NotNull
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public @NotNull Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public @Nullable
	Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("DataObject");
		sb.append("{creationDate=").append(creationDate);
		sb.append(", creatorId=").append(creatorId);
		sb.append(", modificationDate=").append(modificationDate);
		sb.append('}');
		sb.append('\'').append("super class:").append('\'').append(super.toString());
		return sb.toString();
	}
}
