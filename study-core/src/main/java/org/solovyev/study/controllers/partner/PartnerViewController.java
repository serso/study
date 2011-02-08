/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.controllers.partner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.solovyev.study.controllers.CommonController;
import org.solovyev.study.model.PartnerSearchParams;
import org.solovyev.study.model.User;
import org.solovyev.study.resources.ApplicationContextProvider;
import org.solovyev.study.services.PartnerService;

/**
 * User: serso
 * Date: May 31, 2010
 * Time: 10:22:48 PM
 */

@Controller
public class PartnerViewController extends CommonController {

	public static final String PARTNER_MODEL = "partner";

	private PartnerService partnerService;

	@Autowired
	public void setPartnerService(PartnerService partnerService) {
		this.partnerService = partnerService;
	}

	/**
	 * Method prepares data for viewing partner.
	 *
	 * @param partnerId id of partner for display
	 * @param model  model
	 */
	@RequestMapping(value = "/partner/view.do", method = RequestMethod.GET)
	public void prepareForm(@RequestParam(required = true) Integer partnerId, Model model) {
		clearBackButtons(model);
		processViewPartner(partnerId, null, model);
	}

	@RequestMapping(value = "/partner/view.do", method = RequestMethod.POST)
	public void prepareForm(@RequestParam(required = true) Integer partnerId, @RequestParam(required = true) String backRouteAction, Model model) {
		processViewPartner(partnerId, backRouteAction, model);
	}

	private void processViewPartner(Integer partnerId, String backRouteAction, Model model) {
		User applicationUser = ApplicationContextProvider.getApplicationUser();
		if (applicationUser != null) {
			model.addAttribute(PARTNER_MODEL, partnerService.loadPartner(new PartnerSearchParams(partnerId), this.dataSource));
			if (backRouteAction != null) {
				addBackButtonToModel(backRouteAction, model);
			}
		} else {
			throw new IllegalArgumentException("You are not logged in. Please login and try again.");
		}
	}

	@RequestMapping(value = "/partner/back.to.view.do")
	public String back(@RequestParam(required = true) Integer partnerId, Model model) {
		processViewPartner(partnerId, null, model);
		return "/partner/view";
	}
}
