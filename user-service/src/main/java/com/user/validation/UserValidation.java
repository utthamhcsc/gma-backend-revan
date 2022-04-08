package com.user.validation;

import com.user.entities.User;
import com.user.util.Verify;

public final class UserValidation {
	
	
	public  static void validateCreateUser(User user) {
		Verify.verify(!(Verify.isEmpty(user.getEmail())&&Verify.isEmpty(user.getPhoneNumber())), "atleast one input(email/phonenum)");
		
	}

}
