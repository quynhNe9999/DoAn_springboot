package com.sanghuynh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StaffController {
	@RequestMapping(value = "/staff", method=RequestMethod.GET)
	public  String staff (){
		return "staff";
	}
}
