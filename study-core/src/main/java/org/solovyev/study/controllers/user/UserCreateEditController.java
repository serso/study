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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.solovyev.common.definitions.Pair;
import org.solovyev.common.html.Button;
import org.solovyev.common.html.HtmlUtils;
import org.solovyev.study.controllers.CommonController;
import org.solovyev.study.model.DataObjectAction;
import org.solovyev.study.resources.ApplicationContextProvider;
import org.solovyev.study.resources.Config;
import org.solovyev.study.resources.MessageCodes;
import org.solovyev.study.services.UserService;
import org.solovyev.study.validators.UserOnCreateValidator;

/**
 * User: serso
 * Date: Mar 19, 2010
 * Time: 11:59:01 PM
 */
@Controller
@SessionAttributes(UserCreateEditController.USER_MODEL)
public class UserCreateEditController extends CommonController {

	public static final String USER_MODEL = "user";

	@RequestMapping(value = "/user/create.do", method = RequestMethod.GET)
	public void prepareCreateForm(Model model) {
		createUser(model);
	}

	@RequestMapping(value = "/user/create.do", method = RequestMethod.POST)
	public void prepareCreateForm(@RequestParam(required = true) String backRouteAction, Model model) {
		addBackButtonToModel(backRouteAction, model);
		createUser(model);
	}

	private void createUser(Model model) {
		ApplicationContextProvider.getUserStack().clear();
		User user = new User();
		model.addAttribute(USER_MODEL, user);
		ApplicationContextProvider.getUserStack().push(user);
	}

	@RequestMapping(value = "/user/save.new.do", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute(USER_MODEL) User user, BindingResult result, Model model) {
		String view = null;
		this.validator.validate(user, result);
		new UserOnCreateValidator(dataSource).validate(user, result);
		if (!result.hasErrors()) {
			//no errors - let's add new user
			User creator = ApplicationContextProvider.getApplicationUser();
			if (creator != null) {
				user.setCreatorId(creator.getId());
				UserService.insert(user, this.dataSource);
				ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0004.name(), MessageType.info, user.getUsername());

				model.addAttribute(USER_MODEL, new User());
				view = Config.REDIRECT + "/user/back.to.management.do?userId=" + user.getId() + "&action=" + DataObjectAction.create;
			} else {
				ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0008.name(), MessageType.error);
			}
		} else {
			ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0005.name(), MessageType.error);
		}

		if (view == null) {
			view = "/user/create";
		}
		return view;
	}

	@RequestMapping(value = "/user/edit.do", method = RequestMethod.POST)
	public String prepareEditForm(@RequestParam(required = true) Integer userId, @RequestParam(required = true) String backRouteAction, Model model) {
		String view;
		addBackButtonToModel(backRouteAction, model);
		ApplicationContextProvider.getUserStack().clear();
		User user = UserService.loadUser(new UserSearchParams(userId), this.dataSource);
		if (user != null) {
			ApplicationContextProvider.getUserStack().push(user);
			user.setDoNotChangePassword(true);
			model.addAttribute(USER_MODEL, user);
			view = "/user/edit";
		} else {
			view = Config.REDIRECT + "/user/back.to.management.do";
			ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0006.name(), MessageType.error, userId);
		}
		return view;
	}

	@RequestMapping(value = "/user/save.edited.do", method = RequestMethod.POST)
	public String saveEditedUser(@ModelAttribute(USER_MODEL) User user, BindingResult result, @ModelAttribute(BACK_BUTTON_MODEL) Button backButton) {
		String view;

		this.validator.validate(user, result);
		new UserOnCreateValidator(dataSource).validate(user, result);

		if (!result.hasErrors()) {
			//no errors - let's add new user
			UserService.update(user, this.dataSource);
			ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0004.name(), MessageType.info, user.getUsername());
			//view = Config.REDIRECT + "/user/back.to.management.do?userId=" + user.getId() + "&action=" + DataObjectAction.edit;
			view = Config.REDIRECT + HtmlUtils.addRequestParam(backButton.getAction(), Pair.create("userId", String.valueOf(user.getId())), Pair.create("action", DataObjectAction.edit.name()));
		} else {
			view = "/user/edit";
		}
		return view;
	}

	@RequestMapping(value = "/user/enable.do", method = RequestMethod.GET)
	public String enableUser(@RequestParam(required = true) Integer userId, @RequestParam(required = true) Boolean enable) {
		User user = UserService.loadUser(new UserSearchParams(userId), dataSource);

		if (user != null) {
			user.setEnabled(enable);
			UserService.update(user, dataSource);
		} else {
			ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0006.name(), MessageType.error, userId);
		}

		return Config.REDIRECT + "/user/back.to.management.do?userId=" + userId + "&action=" + DataObjectAction.edit;
	}


	@RequestMapping(value = "/user/back.to.create.user.do")
	public String backToCreateUser(Model model) {
		return backToUserManagement(model, "/user/create", "/user/create.do");
	}

	@RequestMapping(value = "/user/back.to.edit.user.do")
	public String backToEdituser(Model model) {
		return backToUserManagement(model, "/user/edit", "/user/edit.do");
	}

	public static String backToUserManagement(Model model, String route, String action) {
		if (!ApplicationContextProvider.getUserStack().isEmpty()) {
			model.addAttribute(UserCreateEditController.USER_MODEL, ApplicationContextProvider.getUserStack().pop());
			return route;
		} else {
			return Config.REDIRECT + action;
		}
	}

	@RequestMapping(value = "/user/remove.linked.partner.do", method = RequestMethod.POST)
	public String removeLinkedPartner(@ModelAttribute(USER_MODEL) User user, @RequestParam(required = true) Integer partnerIndex, @RequestParam(required = true) String currentView) {
		if (partnerIndex >= 0 && partnerIndex < user.getLinkedPartners().size()) {
			user.getLinkedPartners().remove((int) partnerIndex);
		}
		return currentView;
	}
}
