package com.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<User> findByUserNameOrEmailOrPhoneNumber(String userName,String email,String phoneNumber);

	Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);

	Optional<User> findByEmailOrPhoneNumberAndIsActive( String email, String phoneNumber,
			boolean b);
}
