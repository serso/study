/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.controllers;

import org.solovyev.common.html.Button;
import org.solovyev.study.resources.ApplicationContextProvider;
import org.solovyev.study.resources.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: serso
 * Date: Apr 17, 2010
 * Time: 7:43:32 PM
 */

@Controller
public class CommonController implements ApplicationContextAware {

	protected static final String BACK_BUTTON_MODEL = "backButton";
	private static final String BACK_ACTION_URL_PREFIX = "/back.";

	protected WebApplicationContext applicationContext;

	@ModelAttribute(value = BACK_BUTTON_MODEL)
	public Button getBackButton(HttpServletRequest req) {
		if (req.getRequestURL().indexOf(BACK_ACTION_URL_PREFIX) >= 0) {
			if (!ApplicationContextProvider.getBackButtonStack().isEmpty()) {
				ApplicationContextProvider.getBackButtonStack().pop();
			}
		}
		if (!ApplicationContextProvider.getBackButtonStack().isEmpty()) {
			return ApplicationContextProvider.getBackButtonStack().peek().clone();
		}
		return null;
	}

	protected static void addBackButtonToModel(String backRouteAction, Model model) {
		if (backRouteAction != null) {
			final Button backButton = new Button("Back", backRouteAction);
			model.addAttribute(BACK_BUTTON_MODEL, backButton);
			ApplicationContextProvider.getBackButtonStack().push(backButton);
		}
	}

	protected void clearBackButtons(Model model) {
		ApplicationContextProvider.getBackButtonStack().clear();
		model.asMap().remove(BACK_BUTTON_MODEL);
	}

	protected DataSource dataSource = null;
	protected Validator validator = null;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Autowired
	public void setValidator(Validator beanValidator  ) {
		this.validator = beanValidator;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Config.DATE_PATTERN);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		//noinspection AccessStaticViaInstance
		this.applicationContext = (WebApplicationContext)applicationContext;
	}
}