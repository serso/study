/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.controllers.address;

import org.solovyev.study.model.partner.Partner;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.solovyev.common.html.Button;
import org.solovyev.study.controllers.CommonController;
import org.solovyev.study.model.address.Address;
import org.solovyev.study.model.AddressContainer;
import org.solovyev.study.resources.ApplicationContextProvider;
import org.solovyev.study.resources.Config;

/**
 * User: serso
 * Date: May 29, 2010
 * Time: 10:42:57 PM
 */

@Controller
public class AddressCreateEditController extends CommonController {

	public static final String DEFAULT_ROUTER = "/address/management";

	public static final String ADDRESS_CONTAINER_MODEL = "addressContainer";

	@RequestMapping(value = "/address/management.do", method = RequestMethod.GET)
	public void prepareForm(@ModelAttribute(ADDRESS_CONTAINER_MODEL) AddressContainer addressContainer) {
		if (!ApplicationContextProvider.getPartnerStack().isEmpty()) {
			Partner partner = ApplicationContextProvider.getPartnerStack().peek();
			addressContainer.getAddresses().addAll(partner.getAddresses());
		}
	}

	@RequestMapping(value = "/address/save.do", method = RequestMethod.POST)
	public String saveAddresses(@ModelAttribute(ADDRESS_CONTAINER_MODEL) AddressContainer addressContainer, BindingResult result, @ModelAttribute(BACK_BUTTON_MODEL) Button backButton) {
		String view = null;

		this.beanValidator.validate(addressContainer, result);

		if (!result.hasErrors()) {

			//in validator checking on min/max value has been already done
			addressContainer.getAddresses().get(addressContainer.getMainAddressIndex()).setMain(true);

			if (!ApplicationContextProvider.getPartnerStack().isEmpty()) {
				Partner partner = ApplicationContextProvider.getPartnerStack().peek();
				partner.setAddresses(addressContainer.getAddresses());
				if (backButton != null) {
					// todo serso: check if servlet path is correct
					view = Config.REDIRECT + backButton.getAction();
				}
			}

			if (view == null) {
				view = Config.EXCEPTION_PAGE_URL;
			}
		} else {
			view = DEFAULT_ROUTER;
		}

		return view;
	}

	@RequestMapping(value = "/address/remove.do", method = RequestMethod.POST)
	public String removeAddress(@ModelAttribute(ADDRESS_CONTAINER_MODEL) AddressContainer addressContainer, @RequestParam Integer index) {
		if (index >= 0 && index < addressContainer.getAddresses().size()) {
			addressContainer.getAddresses().remove((int) index);
			if (index.equals(addressContainer.getMainAddressIndex()) && !addressContainer.getAddresses().isEmpty()) {
				addressContainer.setMainAddressIndex(0);
			}
		}
		return DEFAULT_ROUTER;
	}

	@RequestMapping(value = "/address/add.do", method = RequestMethod.POST)
	public String addAddress(@ModelAttribute(ADDRESS_CONTAINER_MODEL) AddressContainer addressContainer) {
		addressContainer.getAddresses().add(new Address());
		return DEFAULT_ROUTER;
	}
}