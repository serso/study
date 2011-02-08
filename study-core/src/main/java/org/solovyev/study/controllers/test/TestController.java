/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.controllers.test;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.solovyev.study.controllers.CommonController;
import org.solovyev.study.model.TestPartnerSelector;
import org.solovyev.study.resources.Config;

/**
 * User: serso
 * Date: Apr 29, 2010
 * Time: 9:24:29 PM
 */

@Controller
@SessionAttributes(value = {TestController.TEST_OBJECT_MODEL})
public class TestController extends CommonController {
	public final static String PARTNER_SELECTOR_MODEL = "partnerSelector";
	public final static String TEST_OBJECT_MODEL = "testObject";

	@RequestMapping(value = "test.action.do", method = RequestMethod.GET)
	public void prepareForm(@ModelAttribute(PARTNER_SELECTOR_MODEL) TestPartnerSelector partnerSelector, Model model) {
		partnerSelector.setAvailable(true);
		partnerSelector.setAction("test.select.do");
		model.addAttribute(TEST_OBJECT_MODEL, new TestObject());
	}

	@RequestMapping(value = "back.to.test.action.do")
	public String backToTest(@ModelAttribute(PARTNER_SELECTOR_MODEL) TestPartnerSelector partnerSelector, Model model) {
		partnerSelector.setAvailable(true);
		partnerSelector.setAction("test.select.do");
		return "test.action";
	}

	@RequestMapping(value = "test.select.do", method = RequestMethod.POST)
	public String selectLinkedPartner(@ModelAttribute(PARTNER_SELECTOR_MODEL) TestPartnerSelector partnerSelector, @ModelAttribute(TEST_OBJECT_MODEL) TestObject testObject, Model model) {
		Logger.getLogger(this.getClass()).info("Parnter selector list size = " + partnerSelector.getPartnerIndexes().size());
		testObject.getObjects().add(new TestObject(testObject.getObjects().size() + 1, "test"));
		return Config.REDIRECT + "back.to.test.action.do";
	}

	@RequestMapping(value = "test.action.get.do", method = RequestMethod.POST)
	public String testGet(@RequestParam(required = true) String param, Model model) {
		return "test.action";
	}
}