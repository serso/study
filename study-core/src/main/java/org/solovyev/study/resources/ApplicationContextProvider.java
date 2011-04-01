/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.resources;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.solovyev.common.html.Button;
import org.solovyev.study.model.partner.Partner;
import org.solovyev.study.model.User;

import java.util.Stack;

/**
 * User: serso
 * Date: Mar 30, 2010
 * Time: 1:31:37 AM
 */

@Configuration
public class ApplicationContextProvider implements ApplicationContextAware {

	public static final String MESSAGE_COLLECTOR = "messageCollector";
	public static final String USER_STACK = "userStack";
	public static final String PARTNER_STACK = "partnerStack";
	public static final String BACK_BUTTON_STACK = "backButtonStack";

	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		//noinspection AccessStaticViaInstance
		this.applicationContext = applicationContext;
	}

	@Bean(name = MESSAGE_COLLECTOR)
	public MessageCollector newMessageCollector() {
		return new MessageCollector();
	}

	@Bean(name = USER_STACK)
	public Stack<User> newUserStack() {
		return new Stack<User>();
	}

	@Bean(name = PARTNER_STACK)
	public Stack<Partner> newPartnerStack() {
		return new Stack<Partner>();
	}

	@Bean(name = BACK_BUTTON_STACK)
	public Stack<Button> newBackButtonStack() {
		return new Stack<Button>();
	}

	public static User getApplicationUser() {
		User result = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user instanceof User) {
				result = ((User) user);
			}
		}
		return result;
	}

	public static MessageCollector getMessageCollector() {
		return (MessageCollector) getApplicationContext().getBean(MESSAGE_COLLECTOR);
	}

	public static Stack<User> getUserStack() {
		//noinspection unchecked
		return (Stack<User>) ApplicationContextProvider.getApplicationContext().getBean(USER_STACK);
	}

	public static Stack<Partner> getPartnerStack() {
		//noinspection unchecked
		return (Stack<Partner>) ApplicationContextProvider.getApplicationContext().getBean(PARTNER_STACK);
	}

	public static Stack<Button> getBackButtonStack() {
		//noinspection unchecked
		return (Stack<Button>) ApplicationContextProvider.getApplicationContext().getBean(BACK_BUTTON_STACK);
	}
}
