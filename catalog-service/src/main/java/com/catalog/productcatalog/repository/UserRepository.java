package com.catalog.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.productcatalog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
