package com.ishwor.crm.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ishwor.crm.DAO.CustomerDAO;
import com.ishwor.crm.entity.Customer;

@Controller
@RequestMapping("customer")
public class CustomerController {

	public static Logger logger = Logger.getLogger(CustomerController.class);

	@Autowired
	private CustomerDAO customerDAO;

	@RequestMapping("list")
	private String customerHome(Model model) {
		logger.info("customer index method browsed");

		List<Customer> customer = customerDAO.getCustomer();

		model.addAttribute("customer", customer);

		return "/customer/index";
	}
}
