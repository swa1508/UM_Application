package com.umapp.core.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.umapp.core.rest.IUserController;
import com.umapp.core.service.IUserService;

@RestController
public class UserController implements IUserController {
	
	@Autowired
	private IUserService userService; 

}
