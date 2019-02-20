package com.capgemini.cartservice.cartresource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.cartservice.entity.Cart;
import com.capgemini.cartservice.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartResource {
	
	@Autowired
	private CartService service;

	@GetMapping
	public ResponseEntity<List<Cart>> getAllCarts() {
		List<Cart> carts = service.getallcarts();
		return new ResponseEntity<>(carts, HttpStatus.OK);
	}

	@PostMapping
	public void addCart(@RequestBody Cart cart) {
		service.addCart(cart);
	}

	@GetMapping("/{cartid}")
	public ResponseEntity<Cart> getCart(@PathVariable int cartid) {
		Cart cart = service.getcartById(cartid);
		System.out.println(cart);
		if (cart == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Cart> updateCart(@RequestBody Cart cart) {
		Cart updateCart= service.addToCart(cart);
		return new ResponseEntity<>(updateCart, HttpStatus.OK);
	}

	@PutMapping("/{cartid}")
	public ResponseEntity<Cart> deleteFromCart(@RequestBody Cart cart) {
		Cart updateCart = service.removeFromCart(cart);
		return new ResponseEntity<>(updateCart, HttpStatus.OK);
	}
}
