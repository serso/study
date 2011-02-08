/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.controllers.partner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.solovyev.common.definitions.MessageType;
import org.solovyev.common.html.Button;
import org.solovyev.study.controllers.CommonController;
import org.solovyev.study.controllers.user.UserCreateEditController;
import org.solovyev.study.model.*;
import org.solovyev.study.resources.ApplicationContextProvider;
import org.solovyev.study.resources.Config;
import org.solovyev.study.resources.MessageCodes;
import org.solovyev.study.services.PartnerService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: serso
 * Date: Apr 17, 2010
 * Time: 10:25:31 PM
 */

@Controller
@SessionAttributes({
		PartnerSearchController.PARTNER_SEARCH_PARAMS_MODEL,
		PartnerSearchController.PARTNER_SELECTOR_MODEL,
		PartnerSearchController.PARTNERS_MODEL})
public class PartnerSearchController extends CommonController {

	private PartnerService partnerService;

	@Autowired
	public void setPartnerService(PartnerService partnerService) {
		this.partnerService = partnerService;
	}

	public static final String PARTNER_SEARCH_PARAMS_MODEL = "partnerSearchParams";
	public static final String PARTNER_SELECTOR_MODEL = "partnerSelector";
	public static final String PARTNERS_MODEL = "partners";

	@ModelAttribute(value = PARTNER_SEARCH_PARAMS_MODEL)
	public PartnerSearchParams getPartnerSearchParams() {
		return new PartnerSearchParams(false);
	}

	@ModelAttribute(value = PARTNER_SELECTOR_MODEL)
	public Selector getPartnerSelector() {
		Selector selector = new SelectorImpl();
		selector.getButtons().add(new Button("Delete", "/partner/delete.partners.do"));
		return selector;
	}

	@ModelAttribute(value = PARTNERS_MODEL)
	public List<Partner> getPartners() {
		return new ArrayList<Partner>();
	}

	@RequestMapping(value = {"/partner/back.to.management.do","/partner/_back.to.management.do"})
	public String back(@RequestParam(required = false) Integer partnerId, @RequestParam(required = false) DataObjectAction action, @ModelAttribute(PARTNERS_MODEL) List<Partner> partners) {
		if (partnerId != null && action != null) {
			switch (action) {
				case create:
					Partner createdPartner = partnerService.loadPartner(new PartnerSearchParams(partnerId), this.dataSource);
					if (createdPartner != null) {
						partners.add(createdPartner);
					}
					break;
				case delete:
					Iterator<Partner> it = partners.iterator();
					while (it.hasNext()) {
						if (it.next().getId().equals(partnerId)) {
							it.remove();
						}
					}
					break;
				case edit:
					Partner reloadedPartner = partnerService.loadPartner(new PartnerSearchParams(partnerId), this.dataSource);

					if (reloadedPartner != null) {
						int index = partners.indexOf(reloadedPartner);
						if (index >= 0) {
							partners.remove(index);
							partners.add(index, reloadedPartner);
						}
					}
					break;
			}
		}
		return "/partner/management";
	}

	@RequestMapping(value = "/partner/management.do", method = RequestMethod.GET)
	public void prepareForm(@ModelAttribute(PARTNER_SELECTOR_MODEL) Selector partnerSelector, Model model) {
		clearBackButtons(model);
		partnerSelector.getButtons().clear();
		partnerSelector.getButtons().add(new Button("Delete", "/partner/delete.partners.do"));
		partnerSelector.getIndexes().clear();

		ApplicationContextProvider.getUserStack().clear();
	}

	@RequestMapping(value = "/partner/search.do", method = RequestMethod.POST)
	public String searchUsers(@ModelAttribute(PARTNER_SEARCH_PARAMS_MODEL) PartnerSearchParams partnerSearchParams, Model model) {
		model.addAttribute(PARTNERS_MODEL, partnerService.loadPartners(partnerSearchParams, dataSource));
		return "/partner/management";
	}


	@RequestMapping(value = "/user/add.linked.partner.do", method = RequestMethod.POST)
	public String prepareFormForSelect(@ModelAttribute(UserCreateEditController.USER_MODEL) User user, @ModelAttribute(PARTNER_SELECTOR_MODEL) Selector partnerSelector,
	                                   @RequestParam(required = true) String backRouteAction, Model model) {
		if (ApplicationContextProvider.getUserStack().empty()) {
			throw new IllegalArgumentException("No user found in session");
		}
		//we have to copy linked partners as they are not bind during form submit
		User userFromStack = ApplicationContextProvider.getUserStack().pop();
		user.setLinkedPartners(userFromStack.getLinkedPartners());
		ApplicationContextProvider.getUserStack().push(user);

		((SelectorImpl) partnerSelector).setAvailable(true);
		partnerSelector.getButtons().clear();
		partnerSelector.getButtons().add(new Button("Select", "/partner/select.linked.partner.do"));
		partnerSelector.getButtons().add(new Button("Delete", "/partner/delete.partners.do"));
		partnerSelector.getIndexes().clear();

		addBackButtonToModel(backRouteAction, model);

		return "/partner/management";
	}

	@RequestMapping(value = "/partner/select.linked.partner.do", method = RequestMethod.POST)
	public String selectLinkedPartner(@ModelAttribute(PARTNER_SELECTOR_MODEL) Selector partnerSelector, @ModelAttribute(PARTNERS_MODEL) List<Partner> partners, @ModelAttribute(BACK_BUTTON_MODEL) Button backButton) {

		if (!ApplicationContextProvider.getUserStack().isEmpty()) {
			User user = ApplicationContextProvider.getUserStack().peek();
			user.getLinkedPartners().addAll(partnerSelector.getSelectedItems(partners));
		}

		return Config.REDIRECT + backButton.getAction();
	}

	@RequestMapping(value = "/partner/delete.partners.do", method = RequestMethod.POST)
	public String deletePartner(@ModelAttribute(PartnerSearchController.PARTNER_SELECTOR_MODEL) SelectorImpl selector, @ModelAttribute(PartnerSearchController.PARTNERS_MODEL) List<Partner> partners) {
		List<Partner> selectedPartners = selector.getSelectedItems(partners);
		for (Partner partner : selectedPartners) {
			//delete from database
			partnerService.delete(partner.getId(), this.dataSource);
			//and delete from main search list
			partners.remove(partner);
		}
		selector.clear();

		if (selectedPartners.size() > 0) {
			ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0015.name(), MessageType.info, selectedPartners.size());
		} else {
			ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0016.name(), MessageType.warning);
		}
		return Config.REDIRECT + "/partner/_back.to.management.do";
	}
}
