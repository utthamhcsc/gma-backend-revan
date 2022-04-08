package com.user.util;

import org.springframework.lang.Nullable;

import com.user.exception.VerifyException;

public final class  Verify {
	
	static public void verify(Boolean condition,String message) {
		if(!condition) {
			throw new VerifyException(message);
		}
	}
	public static boolean isEmpty(@Nullable Object str) {
		return (str == null || "".equals(str));
	}

}
