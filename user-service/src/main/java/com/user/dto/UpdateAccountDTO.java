package com.user.dto;

import lombok.Data;

@Data
public class UpdateAccountDTO {
	
	private String userName;
	private String email;
	private String newEmail;
	private String newPhoneNumber;
	private String phoneNumber;
	private String password;
	private String address;

}
