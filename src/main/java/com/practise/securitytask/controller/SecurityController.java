package com.practise.securitytask.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security/api/v1")
public class SecurityController {
	

	@GetMapping("/admin/dashboard")
	public ResponseEntity<String> hellomethod(){
		return ResponseEntity.ok("Admin Page");
	}
	
	@GetMapping("/user/dashboard")
	public ResponseEntity<String> byemethod(){
		return ResponseEntity.ok("User Page");
	}
	
}
