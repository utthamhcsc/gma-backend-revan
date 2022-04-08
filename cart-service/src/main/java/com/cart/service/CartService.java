package com.cart.service;

import java.util.List;

import com.cart.entity.CartItem;

public interface CartService {

	boolean addItemToCart(CartItem cartItem);
	boolean removeItemFromCart(CartItem cartItem);
	boolean clearCart(String userName);
	boolean checkOutItems(List<CartItem> cartItems); 
}
