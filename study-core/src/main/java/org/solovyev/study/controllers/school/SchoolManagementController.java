/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.controllers.school;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.solovyev.common.definitions.MessageImpl;
import org.solovyev.common.definitions.MessageType;
import org.solovyev.common.html.Button;
import org.solovyev.study.controllers.CommonController;
import org.solovyev.study.model.partner.Partner;
import org.solovyev.study.model.partner.PartnerRole;
import org.solovyev.study.model.PartnerSearchParams;
import org.solovyev.study.resources.ApplicationContextProvider;
import org.solovyev.study.resources.Config;
import org.solovyev.study.resources.MessageCodes;
import org.solovyev.study.services.PartnerService;

/**
 * User: serso
 * Date: May 7, 2010
 * Time: 10:47:21 PM
 */

@Controller
@SessionAttributes({SchoolManagementController.SCHOOL_MODEL})
public class SchoolManagementController extends CommonController {

	private PartnerService partnerService;

	@Autowired
	public void setPartnerService(PartnerService partnerService) {
		this.partnerService = partnerService;
	}

	public static final String SCHOOL_MODEL = "school";
	public static final String DEFAULT_ROUTE = "/school/management";

	@RequestMapping(value = "/school/management.do", method = RequestMethod.POST)
	public String prepareForm(@RequestParam(required = true) Integer schoolId, @RequestParam(required = true) String backRouteAction, Model model) {
		return prepareSchoolManagementForm(schoolId, backRouteAction, model);
	}

	@RequestMapping(value = "/school/management.do", method = RequestMethod.GET)
	public String prepareForm(@RequestParam(required = true) Integer schoolId, Model model) {
		clearBackButtons(model);
		return prepareSchoolManagementForm(schoolId, null, model);
	}

	@NotNull
	private String prepareSchoolManagementForm(@NotNull Integer schoolId, @Nullable String backRouteAction, @NotNull Model model) {
		String view = DEFAULT_ROUTE;
		PartnerSearchParams partnerSearchParams = new PartnerSearchParams(schoolId);
		partnerSearchParams.getPartnerRoles().add(PartnerRole.school);
		Partner partner = partnerService.loadPartner(partnerSearchParams, this.dataSource);

		if (backRouteAction != null) {
			addBackButtonToModel(backRouteAction, model);
		}
		if (partner != null) {
			model.addAttribute(SCHOOL_MODEL, partner);
			ApplicationContextProvider.getPartnerStack().clear();
			ApplicationContextProvider.getPartnerStack().push(partner);
		} else {
			view = Config.EXCEPTION_PAGE_URL;
			ApplicationContextProvider.getMessageCollector().addMessage(new MessageImpl(MessageCodes.msg0013.name(), MessageType.error, schoolId));
		}
		return view;
	}

	@RequestMapping(value = "/school/back.to.management.do")
	public String backToSchoolManagement(@ModelAttribute(BACK_BUTTON_MODEL) Button button) {
		return DEFAULT_ROUTE;
	}
}