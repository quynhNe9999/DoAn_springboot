package com.quynhtadinh.demo.controller;

import java.util.List;

import com.quynhtadinh.demo.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quynhtadinh.demo.entity.Users;

@Controller
public class UserController {
	@Autowired
	private UserDAO userDAO;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String user(Model model) {
		List<Users> listUsers = userDAO.findAll();
		model.addAttribute("listUsers", listUsers);
		return "user";
	}

	@RequestMapping(value = "/user/db", method = RequestMethod.GET)
	public String adminDb() {
		return "user db";
	}
}
