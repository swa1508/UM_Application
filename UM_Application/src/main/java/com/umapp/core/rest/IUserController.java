package com.umapp.core.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface IUserController {
	
	@GetMapping("/check")
	public String checkUrl();
	
	
	

}
