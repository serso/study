/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.controllers.test;

import org.springframework.util.AutoPopulatingList;
import org.solovyev.common.definitions.Identity;

/**
 * User: serso
 * Date: May 3, 2010
 * Time: 11:44:27 AM
 */
public class TestObject extends Identity<Integer>{

	private String property;

	private AutoPopulatingList objects = new AutoPopulatingList(TestObject.class);

	public TestObject(Integer id, String property) {
		super(id);
		this.property = property;
	}

	public TestObject(Integer id) {
		super(id);
	}

	public TestObject() {
		super(null);
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public AutoPopulatingList getObjects() {
		return objects;
	}

	public void setObjects(AutoPopulatingList objects) {
		this.objects = objects;
	}
}
