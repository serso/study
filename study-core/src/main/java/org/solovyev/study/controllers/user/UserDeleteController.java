/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.solovyev.common.definitions.MessageType;
import org.solovyev.study.controllers.CommonController;
import org.solovyev.study.exceptions.DatabaseException;
import org.solovyev.study.model.DataObjectAction;
import org.solovyev.study.model.User;
import org.solovyev.study.resources.ApplicationContextProvider;
import org.solovyev.study.resources.Config;
import org.solovyev.study.resources.MessageCodes;
import org.solovyev.study.services.UserService;

/**
 * User: serso
 * Date: Apr 5, 2010
 * Time: 11:45:05 PM
 */

@Controller
public class UserDeleteController extends CommonController {

	@RequestMapping(value = "/user/delete.do", method = RequestMethod.GET)
	public String deleteUsre(@RequestParam(required = true) Integer userId) {
		try {
			User deletedUser = UserService.delete(userId, this.dataSource);
			if (deletedUser != null) {
				ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0007.name(), MessageType.info, deletedUser.getUsername());
			} else {
				ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0006.name(), MessageType.error, userId);
			}
		} catch (DatabaseException e) {
			ApplicationContextProvider.getMessageCollector().addMessage(e.getMsg());
		}
		return Config.REDIRECT + "/user/back.to.management.do?userId=" + userId + "&action=" + DataObjectAction.delete;
	}
}