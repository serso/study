/*
 * Copyright (c) 2009-2011. Created by Sergey Solovyev (aka serso).
 *
 * For more information, please, contact mail@sergey.solovyev.org
 */

package org.solovyev.study.exceptions;

import org.solovyev.common.definitions.Message;

/**
 * User: serso
 * Date: Apr 11, 2010
 * Time: 1:37:48 AM
 */
public class DatabaseException extends Exception{

	private Message msg;

	public DatabaseException(Message msg) {
		this.msg = msg;
	}

	public Message getMsg() {
		return msg;
	}
}
