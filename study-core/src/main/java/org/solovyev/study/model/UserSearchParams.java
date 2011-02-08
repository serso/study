/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model;

/**
 * User: serso
 * Date: Mar 27, 2010
 * Time: 7:37:19 PM
 */
public class UserSearchParams extends User implements StrictSearch {

	private boolean strictSearch = true;

	private boolean fullLoad = true;

	public UserSearchParams() {
	}

	public UserSearchParams(boolean strictSearch) {
		this.strictSearch = strictSearch;
	}

	public UserSearchParams(Integer id) {
		super(id);
	}

	public boolean isStrictSearch() {
		return strictSearch;
	}

	public void setStrictSearch(boolean strictSearch) {
		this.strictSearch = strictSearch;
	}

	public boolean isFullLoad() {
		return fullLoad;
	}

	public void setFullLoad(boolean fullLoad) {
		this.fullLoad = fullLoad;
	}
}
