/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.controllers.user;

import org.solovyev.common.msg.MessageType;
import org.solovyev.study.model.user.User;
import org.solovyev.study.model.user.UserSearchParams;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.solovyev.common.html.Button;
import org.solovyev.study.controllers.CommonController;
import org.solovyev.study.exceptions.DatabaseException;
import org.solovyev.study.model.*;
import org.solovyev.study.resources.ApplicationContextProvider;
import org.solovyev.study.resources.Config;
import org.solovyev.study.resources.MessageCodes;
import org.solovyev.study.services.UserService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: serso
 * Date: Mar 27, 2010
 * Time: 7:18:27 PM
 */
@Controller
@SessionAttributes({UserSearchController.USERS_MODEL, UserSearchController.USER_SEARCH_PARAMS_MODEL, UserSearchController.USER_SELECTOR_MODEL})
public class UserSearchController extends CommonController {

	//model attributes
	public static final String USERS_MODEL = "users";
	public static final String USER_SEARCH_PARAMS_MODEL = "userSearchParams";
	public static final String USER_SELECTOR_MODEL = "userSelector";

	public static final String DEFAULT_ROUTE = "/user/management";


	/**
	 * Model attribute is responsible for users' select
	 *
	 * @return selector object
	 */
	@ModelAttribute(value = USER_SELECTOR_MODEL)
	public Selector getUserSelector() {
		return new SelectorImpl();
	}

	/**
	 * Model attribute is responsible for user search parameters
	 *
	 * @return user search parameters
	 */
	@ModelAttribute(value = USER_SEARCH_PARAMS_MODEL)
	public UserSearchParams getUserSearchParams() {
		return new UserSearchParams(false);
	}

	/**
	 * Model attribute is responsible for storing list of found users.
	 *
	 * @return new empty list of users
	 */
	@ModelAttribute(value = USERS_MODEL)
	public List<User> getUsers() {
		return new ArrayList<User>();
	}

	/**
	 * Prepare user search form
	 *
	 * @param userSelector user selector object
	 */
	@RequestMapping(value = "/user/management.do", method = RequestMethod.GET)
	public void prepareForm(@ModelAttribute(USER_SELECTOR_MODEL) SelectorImpl userSelector, Model model) {
		clearBackButtons(model);
		userSelector.clear();
		userSelector.getButtons().clear();
		userSelector.getButtons().add(new Button("Delete", "/user/delete.users.do"));
	}

	/**
	 * Do search
	 *
	 * @param userSearchParams user search parameters
	 * @param model            model
	 * @return view
	 */
	@RequestMapping(value = "/user/search.do", method = RequestMethod.POST)
	public String searchUsers(@ModelAttribute(USER_SEARCH_PARAMS_MODEL) UserSearchParams userSearchParams, Model model) {
		//load users
		List<User> users = UserService.loadUsers(userSearchParams, dataSource);
		//and put them into the model
		model.addAttribute(USERS_MODEL, users);
		return DEFAULT_ROUTE;
	}

	/**
	 * Method processes different back actions
	 *
	 * @param userId id of affected user
	 * @param action action which affects user
	 * @param users  users model
	 * @param button back button
	 * @return view
	 */
	@RequestMapping(value = "/user/back.to.management.do")
	public String prepareForm(@RequestParam(required = false) Integer userId, @RequestParam(required = false) DataObjectAction action, @ModelAttribute(USERS_MODEL) List<User> users, @ModelAttribute(BACK_BUTTON_MODEL) Button button) {
		if (userId != null && action != null) {
			switch (action) {
				case create:
					//after creation put created user into users' list
					User createdUser = UserService.loadUser(new UserSearchParams(userId), this.dataSource);
					if (createdUser != null) {
						users.add(createdUser);
					}
					break;
				case delete:
					//after delete - remove deleted user from list
					Iterator<User> it = users.iterator();
					while (it.hasNext()) {
						if (it.next().getId().equals(userId)) {
							it.remove();
						}
					}
					break;
				case edit:
					//after update - change updated user in the list
					User reloadedUser = UserService.loadUser(new UserSearchParams(userId), this.dataSource);

					if (reloadedUser != null) {
						int index = users.indexOf(reloadedUser);
						if (index >= 0) {
							users.remove(index);
							users.add(index, reloadedUser);
						}
					}
					break;
			}
		}
		return DEFAULT_ROUTE;
	}

	@RequestMapping(value = "/user/delete.users.do", method = RequestMethod.POST)
	public String deleteUser(@ModelAttribute(UserSearchController.USER_SELECTOR_MODEL) SelectorImpl selector, @ModelAttribute(UserSearchController.USERS_MODEL) List<User> users) {
		List<User> selectedUsers = selector.getSelectedItems(users);
		for (User user : selectedUsers) {
			try {
				//delete from database
				UserService.delete(user.getId(), this.dataSource);
				//and delete from main search list
				users.remove(user);
			} catch (DatabaseException e) {
				ApplicationContextProvider.getMessageCollector().addMessage(e.getMsg());
			}
		}
		selector.clear();

		if (selectedUsers.size() > 0) {
			ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0001.name(), MessageType.info, selectedUsers.size());
		} else {
			ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0002.name(), MessageType.warning);
		}
		return Config.REDIRECT + "/user/back.to.management.do";
	}
}