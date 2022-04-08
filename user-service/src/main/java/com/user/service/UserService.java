package com.user.service;

import java.util.Map;

import com.user.dto.AuthenticateRequest;
import com.user.dto.AuthenticationResponse;
import com.user.dto.UpdateAccountDTO;
import com.user.dto.UpdatePasswordDTO;
import com.user.entities.User;

public interface UserService {
	User createAccount(User user);

	Map<String,String> updatePassword(UpdatePasswordDTO updatePasswordDTO);
	Map<String,String> accountDeactivate(User user);
	
	User updateAddress(User user);
	User updateAccount(UpdateAccountDTO updateAccountDTO);

	

	AuthenticationResponse login(AuthenticateRequest authenticationRequest);
}
