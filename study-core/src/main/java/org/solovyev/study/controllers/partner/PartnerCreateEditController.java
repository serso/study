/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.controllers.partner;

import org.solovyev.common.msg.MessageType;
import org.solovyev.study.model.partner.*;
import org.solovyev.study.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.solovyev.common.definitions.Pair;
import org.solovyev.common.html.Button;
import org.solovyev.common.html.HtmlUtils;
import org.solovyev.study.controllers.CommonController;
import org.solovyev.study.model.*;
import org.solovyev.study.resources.ApplicationContextProvider;
import org.solovyev.study.resources.Config;
import org.solovyev.study.resources.MessageCodes;
import org.solovyev.study.services.PartnerService;
import org.solovyev.study.validators.PartnerOnCreateValidator;

/**
 * User: serso
 * Date: Apr 17, 2010
 * Time: 5:18:16 PM
 */
@Controller
@SessionAttributes(PartnerCreateEditController.PARTNER_MODEL)
public class PartnerCreateEditController extends CommonController {

	public final static String PARTNER_MODEL = "partner";

	private PartnerService partnerService;

	@Autowired
	public void setPartnerService(PartnerService partnerService) {
		this.partnerService = partnerService;
	}

	@RequestMapping(value = "/partner/create.do", method = RequestMethod.GET)
	public void prepareCreateForm(@RequestParam(required = false) PartnerType type, Model model) {
		clearBackButtons(model);
		addPartnerToTheModel(type, model);
	}

	@RequestMapping(value = "/partner/create.do", method = RequestMethod.POST)
	public void prepareCreateForm(@RequestParam(required = false) PartnerType type, @RequestParam(required = false) String backRouteAction, Model model) {
		addBackButtonToModel(backRouteAction, model);
		addPartnerToTheModel(type, model);
	}

	private void addPartnerToTheModel(PartnerType type, Model model) {
		if (type == null || type == PartnerType.natural_person) {
			model.addAttribute(PARTNER_MODEL, new NaturalPerson(null));
		} else {
			model.addAttribute(PARTNER_MODEL, new LegalPerson(null));
		}
	}

	@RequestMapping(value = "/partner/edit.do", method = RequestMethod.GET)
	public String prepareEditForm(@RequestParam(required = true) Integer partnerId, Model model) {
		clearBackButtons(model);
		return preaparePartnerForEdit(partnerId, model);
	}

	@RequestMapping(value = "/partner/edit.do", method = RequestMethod.POST)
	public String prepareEditForm(@RequestParam(required = true) Integer partnerId, @RequestParam(required = true) String backRouteAction, Model model) {
		addBackButtonToModel(backRouteAction, model);
		return preaparePartnerForEdit(partnerId, model);
	}

	private String preaparePartnerForEdit(Integer partnerId, Model model) {
		String view;
		Partner partner = partnerService.loadPartner(new PartnerSearchParams(partnerId), this.dataSource);
		if (partner != null) {
			view = "/partner/edit";
			model.addAttribute(PARTNER_MODEL, partner);
		} else {
			view = Config.EXCEPTION_PAGE_URL;
			ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0013.name(), MessageType.error, partnerId);
		}
		return view;
	}

	@RequestMapping(value = "/partner/save.new.do", method = RequestMethod.POST)
	public String savePartner(@ModelAttribute(PARTNER_MODEL) Partner partner, BindingResult result, Model model) {
		String view = null;
		this.validator.validate(partner, result);

		new PartnerOnCreateValidator(dataSource).validate(partner, result);

		if (!result.hasErrors()) {
			//no errors - let's add new partner
			User creator = ApplicationContextProvider.getApplicationUser();
			if (creator != null) {
				//set creator
				partner.setCreatorId(creator.getId());
				//and add partner
				partnerService.insert(partner, this.dataSource);
				//add message
				ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0011.name(), MessageType.info, partner.getFullName());

				//add proper partner to the model
				addPartnerToTheModel(partner.getPartnerType(), model);
				view = Config.REDIRECT + "/partner/back.to.management.do?partnerId=" + partner.getId() + "&action=" + DataObjectAction.create;
			} else {
				ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0008.name(), MessageType.error);
			}
		}

		if (view == null) {
			//somewhere was fail => let's go back
			view = "/partner/create";
		}
		return view;
	}

	@RequestMapping(value = "/partner/save.edited.do", method = RequestMethod.POST)
	public String saveEditedPartner(@ModelAttribute(PARTNER_MODEL) Partner partner, BindingResult result, @ModelAttribute(BACK_BUTTON_MODEL) Button backButton) {
		String view;

		this.validator.validate(partner, result);
		new PartnerOnCreateValidator(dataSource).validate(partner, result);

		if (!result.hasErrors()) {
			//no errors - let's add new user
			partnerService.update(partner, this.dataSource);
			ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0011.name(), MessageType.info, partner.getFullName());
			//view = Config.REDIRECT + "/partner/back.to.management.do?partnerId=" + partner.getId() + "&action=" + DataObjectAction.edit;
			view = Config.REDIRECT + HtmlUtils.addRequestParam(backButton.getAction(), Pair.create("partnerId", String.valueOf(partner.getId())), Pair.create("action", DataObjectAction.edit.name()));
		} else {
			view = "/partner/edit";
		}
		return view;
	}

	@RequestMapping(value = "/partner/edit.addresses.do", method = RequestMethod.POST)	
	public String editAddresses (@ModelAttribute(PARTNER_MODEL) Partner partner, @RequestParam(required = false) String backRouteAction, Model model) {
		ApplicationContextProvider.getPartnerStack().push(partner);
		addBackButtonToModel(backRouteAction, model);
		return Config.REDIRECT + "/address/management.do";
	}

	@RequestMapping(value = "/partner/edit.details.do", method = RequestMethod.POST)
	public String editDetails (@ModelAttribute(PARTNER_MODEL) Partner partner, @RequestParam String backRouteAction, Model model) {
		ApplicationContextProvider.getPartnerStack().push(partner);
		addBackButtonToModel(backRouteAction, model);
		if (partner.getPartnerRoles().contains(PartnerRole.school)) {
			return Config.REDIRECT + "/school/details/edit.do";
		} else {
			ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.unsupported.name(), MessageType.error);
			return Config.REDIRECT + Config.EXCEPTION_PAGE_URL;
		}
	}

	@RequestMapping(value = "/partner/back.to.edit.do")
	public String backToEdit (Model model) {
		return backToCreateEdit(model, "/partner/edit");
	}

	@RequestMapping(value = "/partner/back.to.create.do")
	public String backToCreate (Model model) {
		return backToCreateEdit(model, "/partner/create");
	}

	public static String backToCreateEdit(Model model, String route) {
		if (!ApplicationContextProvider.getPartnerStack().isEmpty()) {
			model.addAttribute(PARTNER_MODEL, ApplicationContextProvider.getPartnerStack().pop());
			return route;
		} else {
			return Config.EXCEPTION_PAGE_URL;
		}
	}
}