package com.education.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.education.entity.ResponseBody;
import com.education.service.UserService;

@RequestMapping("/user")
@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<ResponseBody> login(@RequestHeader String username , @RequestHeader String password){
		return this.userService.login(username , password);
	}
}
