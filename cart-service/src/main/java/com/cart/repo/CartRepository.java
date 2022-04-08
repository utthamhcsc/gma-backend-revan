package com.cart.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cart.entity.CartId;
import com.cart.entity.CartItem;

public interface CartRepository extends JpaRepository<CartItem, CartId>{

}
