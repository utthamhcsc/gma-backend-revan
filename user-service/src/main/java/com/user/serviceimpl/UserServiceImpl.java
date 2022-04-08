package com.user.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.dto.AuthenticateRequest;
import com.user.dto.AuthenticationResponse;
import com.user.dto.UpdateAccountDTO;
import com.user.dto.UpdatePasswordDTO;
import com.user.entities.CustomeUserDetails;
import com.user.entities.User;
import com.user.repository.UserRepository;
import com.user.service.UserService;
import com.user.util.JwtUtil;
import com.user.util.Verify;
import com.user.validation.UserValidation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthenticationManager authenticationManager;
	
	private final CustomUserDetailsService customUserDetailsService;
	private final JwtUtil jwtUtil;

	@Override
	public User createAccount(User user) {
	UserValidation.validateCreateUser(user);
		userRepository
		.findByEmailOrPhoneNumberAndIsActive(user.getEmail(),user.getPhoneNumber(),true)
		.ifPresent(existUser->{		
			Verify.verify(!existUser.getEmail().equalsIgnoreCase(user.getEmail()),"User Already Exist with this email");
			Verify.verify(!existUser.getPhoneNumber().equalsIgnoreCase(user.getPhoneNumber()),"User Already Exist with this phonenumber");	
		});
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	
	@Override
	public User updateAccount(UpdateAccountDTO updateAccountDTO) {
		userRepository
		.findByEmailOrPhoneNumberAndIsActive(updateAccountDTO.getNewEmail(),updateAccountDTO.getNewPhoneNumber(),true)
		.ifPresent(existUser->{		
			Verify.verify(!existUser.getEmail().equalsIgnoreCase(updateAccountDTO.getNewEmail()),"User Already Exist with this email");
			Verify.verify(!existUser.getPhoneNumber().equalsIgnoreCase(updateAccountDTO.getNewPhoneNumber()),"User Already Exist with this phonenumber");	
		});
		return userRepository
		.findByEmailOrPhoneNumberAndIsActive(updateAccountDTO.getEmail(),updateAccountDTO.getPhoneNumber(),true)
		.map(existingUser->{
			if(!Verify.isEmpty(updateAccountDTO.getNewEmail()))existingUser.setEmail(updateAccountDTO.getNewEmail());
			if(!Verify.isEmpty(updateAccountDTO.getNewPhoneNumber()))existingUser.setPhoneNumber(updateAccountDTO.getNewPhoneNumber());
			if(!Verify.isEmpty(updateAccountDTO.getAddress()))existingUser.setAddress(updateAccountDTO.getAddress());
			return userRepository.save(existingUser);
		})
		.orElseThrow(()->new RuntimeException("User Not Found"));
		
	}
	

	@Override
	public Map<String, String> updatePassword(UpdatePasswordDTO updatePasswordDTO) {
		userRepository
		.findByEmailOrPhoneNumber(updatePasswordDTO.getEmail(),updatePasswordDTO.getPhoneNumber())
		.map(existingUser->{
			if(!existingUser.getIsActive()) {
				throw new RuntimeException("Account is Deactivated");
			}
			if(!passwordEncoder.matches(existingUser.getPassword(), updatePasswordDTO.getOldPassword())) {
				throw new RuntimeException("Incorrect Password");
			}
			existingUser.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
					return userRepository.save(existingUser);
		})
		.orElseThrow(()->new RuntimeException("User Not Found"));
		
				Map<String,String> map=new HashMap<>();
				return map;
	}

	@Override
	public Map<String, String> accountDeactivate(User user) {
		userRepository
		.findByUserNameOrEmailOrPhoneNumber(user.getUserName(),user.getEmail(),user.getPhoneNumber())
				.map(existingUser->{
					existingUser.setIsActive(false);
					
							return userRepository.save(existingUser);
				})
				
				.orElseThrow(()->new RuntimeException("User Not Found"));
		
		return null;
		
	}

	@Override
	public AuthenticationResponse login(AuthenticateRequest authenticationRequest) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (DisabledException e) {
			throw new RuntimeException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new RuntimeException("INVALID_CREDENTIALS", e);
		}
		final CustomeUserDetails userDetails = (CustomeUserDetails) customUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtUtil.generateToken(userDetails);

		return new AuthenticationResponse(token);
	}


	@Override
	public User updateAddress(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
