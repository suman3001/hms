package com.hms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.entity.AppUser;
import com.hms.payload.AppUserDto;
import com.hms.payload.LoginDto;
import com.hms.service.LoginService;
import com.hms.service.UserService;

@RestController

@RequestMapping("/hmsapi/v1/users")
public class UserController {
	
	private final LoginService loginService;

	private final UserService userService;

	public UserController(UserService userService, LoginService loginService) {
		this.userService = userService;
        this.loginService = loginService;
		
	}
	
	
	

	@PostMapping("/signUp")
	public ResponseEntity<?> createUser(@RequestBody AppUserDto appUserDto) {
		try {
			AppUser savedUser = userService.createUser(appUserDto);
			return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody LoginDto dto){
	       String token = loginService.verifyLogin(dto);
	        if(token!=null){
	            return new ResponseEntity<>(token,HttpStatus.OK);
	        }else {
	            return new ResponseEntity<>("Invalid username/passsword",HttpStatus.FORBIDDEN);
	        }
	    }
}
