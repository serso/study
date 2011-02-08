/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.resources;

/**
 * User: serso
 * Date: Mar 28, 2010
 * Time: 11:48:57 PM
 */
public enum MessageCodes {

	/**
	 * {0} users were successfully deleted.
	 */
	msg0001,

	/**
	 * No users were selected for delete.
	 */
	msg0002,

	//todo serso: change message text
	/**
	 * Hello, you are on user search page!
	 */
	msg0003,

	/**
	 * User {0} has been successfully saved.
	 */
	msg0004,

	/**
	 * Some errors occurred while saving user.
	 */
	msg0005,

	/**
	 * Operation cannot proceed. User with id {0} is not found.
	 */
	msg0006,

	/**
	 * User {0} was successfully deleted.
	 */
	msg0007,

	/**
	 * To provide operation you have to be logged in. Please, login and repeat operation.
	 */
	msg0008,

	/**
	 * User {0} cannot be deleted because he is used in system operations. You can disable him instead.
	 */
	msg0009,

	/**
	 * Your login attempt was not successful, try again. Reason: {0}.
	 */
	msg0010,

	/**
	 * Partner {0} has been successfully saved.
	 */
	msg0011,

	/**
	 * Partner {0} was successfully deleted.
	 */
	msg0012,

	/**
	 * Operation cannot proceed. Partner with id {0} is not found.
	 */
	msg0013,

	/**
	 * At least one partner has to be chosen.
	 */
	msg0014,

	/**
	 * {0} partners were successfully deleted.
	 */
	msg0015,

	/**
	 * No partners were selected for delete.
	 */
	msg0016,

	/**
	 * Operation you want to proceed is currently unsupported.
	 */
	unsupported,


	/**
	 * Internal error occured: {0}.
	 */
	internal_error
}
