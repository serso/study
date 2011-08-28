/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.controllers.user;

import org.solovyev.study.model.user.User;
import org.solovyev.study.model.user.UserRole;
import org.solovyev.study.model.user.UserSearchParams;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.solovyev.study.controllers.CommonController;
import org.solovyev.study.resources.ApplicationContextProvider;
import org.solovyev.study.services.UserService;

/**
 * User: serso
 * Date: May 7, 2010
 * Time: 10:18:02 AM
 */

@Controller
public class UserViewController extends CommonController {

	public static final String USER_MODEL = "user";

	/**
	 * Method prepares data for viewing user.
	 *
	 * @param userId id of user for display
	 * @param model  model
	 */
	@RequestMapping(value = "/user/view.do", method = RequestMethod.GET)
	public void prepareForm(@RequestParam(required = true) Integer userId, Model model) {
		clearBackButtons(model);
		processViewUser(userId, null, model);
	}

	@RequestMapping(value = "/user/view.do", method = RequestMethod.POST)
	public void prepareForm(@RequestParam(required = true) Integer userId, @RequestParam(required = true) String backRouteAction, Model model) {
		processViewUser(userId, backRouteAction, model);
	}

	private void processViewUser(Integer userId, String backRouteAction, Model model) {
		User applicationUser = ApplicationContextProvider.getApplicationUser();
		if (applicationUser != null) {
			if (applicationUser.getId().equals(userId) || applicationUser.hasAnyRoles(UserRole.developer, UserRole.school_employee, UserRole.administrator)) {
				model.addAttribute(USER_MODEL, UserService.loadUser(new UserSearchParams(userId), this.dataSource));
				if ( backRouteAction != null ) {
					addBackButtonToModel(backRouteAction, model);
				}
			} else {
				throw new IllegalArgumentException("Access denied.");
			}
		} else {
			throw new IllegalArgumentException("You are not logged in. Please login and try again.");
		}
	}

	@RequestMapping(value = "/user/back.to.view.do")
	public String back(@RequestParam(required = true) Integer userId, Model model) {
		processViewUser(userId, null, model);
		return "/user/view";
	}
}