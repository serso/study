/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.validators;

import org.springframework.validation.Validator;

import javax.sql.DataSource;

/**
 * User: serso
 * Date: Apr 17, 2010
 * Time: 9:43:00 PM
 */
public abstract class DataSourceValidator implements Validator{

	protected DataSource dataSource;

	public DataSourceValidator(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
