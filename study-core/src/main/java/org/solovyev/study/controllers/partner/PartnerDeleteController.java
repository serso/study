/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.controllers.partner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.solovyev.common.definitions.MessageType;
import org.solovyev.study.controllers.CommonController;
import org.solovyev.study.model.DataObjectAction;
import org.solovyev.study.model.Partner;
import org.solovyev.study.resources.ApplicationContextProvider;
import org.solovyev.study.resources.Config;
import org.solovyev.study.resources.MessageCodes;
import org.solovyev.study.services.PartnerService;

/**
 * User: serso
 * Date: Apr 19, 2010
 * Time: 10:47:49 PM
 */

@Controller
public class PartnerDeleteController extends CommonController {

	private PartnerService partnerService;

	@Autowired
	public void setPartnerService(PartnerService partnerService) {
		this.partnerService = partnerService;
	}

	@RequestMapping(value = "/partner/delete.do", method = RequestMethod.GET)
	public String deletePartner(@RequestParam(required = true) Integer partnerId) {
		Partner deletedPartner = partnerService.delete(partnerId, this.dataSource);
		if (deletedPartner != null) {
			ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0012.name(), MessageType.info, deletedPartner.getFullName());
		} else {
			ApplicationContextProvider.getMessageCollector().addMessage(MessageCodes.msg0013.name(), MessageType.error, partnerId);
		}
		return Config.REDIRECT + "/partner/_back.to.management.do?partnerId=" + partnerId + "&action=" + DataObjectAction.delete;
	}
}