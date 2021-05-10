package com.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping("/")           // this will be accessible to all without authentication
	public String welcomeMsg() {
		return("<h1> Welcome Home </h1>");
	}

	@GetMapping("/user")           // this will be accessible to all with user(role) authentication
	public String welcomeMsgUser() {
		return("<h1> Welcome Home User</h1>");
	}

	@GetMapping("/admin")           // this will be accessible to all admin(role) authentication
	public String welcomeMsgAdmin() {
		return("<h1> Welcome Home Admin</h1>");
	}


}
