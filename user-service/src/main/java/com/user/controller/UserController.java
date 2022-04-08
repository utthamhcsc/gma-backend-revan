package com.user.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.AuthenticateRequest;
import com.user.dto.AuthenticationResponse;
import com.user.dto.UpdateAccountDTO;
import com.user.dto.UpdatePasswordDTO;
import com.user.entities.User;
import com.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/account")
public class UserController {
	private final UserService userService;
	
	@PostMapping
	public User createAccount(@RequestBody User user) {
		return userService.createAccount(user);	
	}
	@PutMapping
	public User updateAccount(@RequestBody UpdateAccountDTO user) {
		return userService.updateAccount(user);
	}
	
@PutMapping("update-password")
	public Map<String,String> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO){
		return userService.updatePassword(updatePasswordDTO);
		
	}
@PutMapping("deactivate")
	public Map<String,String> accountDeactivate(@RequestBody User user){
		return userService.accountDeactivate(user);
		}
@PostMapping("authenticate")
	public AuthenticationResponse login(@RequestBody AuthenticateRequest user){
		return userService.login(user);
		}
}
