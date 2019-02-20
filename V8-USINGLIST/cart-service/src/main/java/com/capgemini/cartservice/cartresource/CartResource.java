package com.capgemini.cartservice.cartresource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.cartservice.entity.Cart;
import com.capgemini.cartservice.entity.Items;
import com.capgemini.cartservice.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartResource {
	
	@Autowired
	private CartService service;

	/**
	 * 
	 * @returns all present carts
	 */
	@GetMapping
	public ResponseEntity<List<Cart>> getAllCarts() {
		List<Cart> carts = service.getallcarts();
		return new ResponseEntity<>(carts, HttpStatus.OK);
	}

	/**
	 * This method adds Cart into the Database.
	 * @param cart
	 */
	@PostMapping("/{cartid}")
	public void addCart(@PathVariable int cartid) {
		Cart cart = new Cart();
		cart.setCartId(cartid);
		service.addCart(cart);
	}
	
	/**
	 * 
	 * @param cartid
	 * @returns cart based on its Id
	 */
	@GetMapping("/{cartid}")
	public ResponseEntity<Cart> getCart(@PathVariable int cartid) {
		Cart cart = service.getcartById(cartid);
		System.out.println(cart);
		if (cart == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	/**
	 * 
	 * @param cart
	 * @returns updated cart - adds product into the cart
	 */
	@PutMapping("/{cartid}")
	public ResponseEntity<Cart> updateCart(@PathVariable int cartid, @RequestBody Cart cart) {
		System.out.println("new products"+cart);
		Cart cartOne = service.getcartById(cartid);
		List<Items> items = new ArrayList<Items>();
		if(cartOne.getItems()!=null)
		{
		items.addAll(cartOne.getItems());
		}
		items.addAll(cart.getItems());
		cartOne.setItems(items);
		Cart updatedCart = service.addToCart(cartOne);
		System.out.println("11111"+updatedCart.getItems());
		//sendCartDetails(updatedCart.getItems());
		/*
		 * cart1.setCartId(updatedCart.getCartId());
		 * cart1.setTotalPrice(service.cartTotal(updatedCart));
		 * cart1.setItems(updatedCart.getItems()); order(cart1);
		 */
		//restTemplate.postForEntity("http://10.246.92.126:9090/orders", updatedCart, null);
		return new ResponseEntity<>(updatedCart, HttpStatus.OK);
	}

	/**
	 * 
	 * @param cart
	 * @returns cart with present items in it
	 */
	@DeleteMapping("/{cartid}")
	public  ResponseEntity<Cart> deleteFromCart(@RequestBody Cart cart) {
		Cart updatedCart = service.removeFromCart(cart);
		return new ResponseEntity<>(updatedCart,HttpStatus.OK);
	}
}
