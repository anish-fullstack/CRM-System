package com.ishwor.crm.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	public static Logger logger = Logger.getLogger(HomeController.class);

	@RequestMapping("/")
	public String homePage() {
		logger.info("inside home controller===>homePage()");

		return "index";
	}
}
