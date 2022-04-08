package com.user.dto;

import lombok.Data;

@Data
public class UpdatePasswordDTO {	
	private String userName;
	private String email;
	private String phoneNumber;
	private String oldPassword;
	private String newPassword;

}
