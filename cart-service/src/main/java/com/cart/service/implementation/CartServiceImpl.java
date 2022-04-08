package com.cart.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cart.entity.CartItem;
import com.cart.repo.CartRepository;
import com.cart.service.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
	
	private final CartRepository cartRepository;

	@Override
	public boolean addItemToCart(CartItem cartItem) {
		try {
			cartRepository.save(cartItem);
			return true;
		}catch(Exception ex) {
			return false;
		}
		
	}

	@Override
	public boolean removeItemFromCart(CartItem cartItem) {
		try {
			cartRepository.save(cartItem);
			return true;
		}catch(Exception ex) {
			return false;
		}
	}

	@Override
	public boolean clearCart(String userName) {
		try {
			//cartRepository.save(cartItem);
			return true;
		}catch(Exception ex) {
			return false;
		}
	}

	@Override
	public boolean checkOutItems(List<CartItem> cartItems) {
		try {
			cartRepository.deleteAll(cartItems);
			return true;
		}catch(Exception ex) {
			return false;
		}
	}

}
