/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.controllers.school;

import org.solovyev.study.model.partner.Partner;
import org.solovyev.study.model.partner.PartnerRole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.solovyev.common.definitions.MessageImpl;
import org.solovyev.common.definitions.MessageType;
import org.solovyev.common.html.Button;
import org.solovyev.study.controllers.CommonController;
import org.solovyev.study.model.SchoolDetails;
import org.solovyev.study.resources.ApplicationContextProvider;
import org.solovyev.study.resources.Config;
import org.solovyev.study.resources.MessageCodes;

/**
 * User: serso
 * Date: May 8, 2010
 * Time: 10:45:47 AM
 */

@Controller
@SessionAttributes({SchoolDetailsController.SCHOOL_MODEL,
		SchoolDetailsController.SCHOOL_DETAILS_MODEL})
public class SchoolDetailsController extends CommonController {

	public static final String SCHOOL_MODEL = "school";
	public static final String SCHOOL_DETAILS_MODEL = "schoolDetails";

	public static final String DEFAULT_ROUTE = "/school/details/edit";

	@RequestMapping(value = "/school/details/edit.do", method = RequestMethod.GET)
	public String prepareFormGet(Model model) {
		return prepareForm(model);
	}

	@RequestMapping(value = "/school/details/edit.do", method = RequestMethod.POST)
	public String prepareFormPost(@RequestParam String backRouteAction, Model model) {
		addBackButtonToModel(backRouteAction, model);
		return prepareForm(model);
	}

	private String prepareForm(Model model) {
		String view = DEFAULT_ROUTE;
		if (!ApplicationContextProvider.getPartnerStack().isEmpty()) {
			Partner partner = ApplicationContextProvider.getPartnerStack().peek();
			model.addAttribute(SCHOOL_MODEL, partner);
			model.addAttribute(SCHOOL_DETAILS_MODEL, partner.getDetails().get(PartnerRole.school));
		} else {
			view = Config.EXCEPTION_PAGE_URL;
			ApplicationContextProvider.getMessageCollector().addMessage(new MessageImpl(MessageCodes.internal_error.name(), MessageType.error, "Partners' stack is empty"));
		}
		return view;
	}

	@RequestMapping(value = "/school/details/save.do", method = RequestMethod.POST)
	public String saveSchoolDetails(@ModelAttribute(SCHOOL_MODEL) Partner school, @ModelAttribute(SCHOOL_DETAILS_MODEL) SchoolDetails schoolDetails, BindingResult result, @ModelAttribute(BACK_BUTTON_MODEL) Button backButton) {
		String view = DEFAULT_ROUTE;

		this.validator.validate(schoolDetails, result);

		if (!result.hasErrors()) {
			//here is the same object as in session partners' stack
			//so changing here we also change and session object in the stack
			school.getDetails().put(PartnerRole.school, schoolDetails);

			if (backButton != null) {
				view = Config.REDIRECT + backButton.getAction();
			} else {
				view = Config.EXCEPTION_PAGE_URL;
				ApplicationContextProvider.getMessageCollector().addMessage(new MessageImpl(MessageCodes.internal_error.name(), MessageType.error, "Back buttons' stack is empty"));
			}
		}

		return view;
	}
}