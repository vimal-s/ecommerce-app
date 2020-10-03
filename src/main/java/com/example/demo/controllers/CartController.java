package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addToCart(@RequestBody ModifyCartRequest request) {
        logger.info("Received request to add to cart " +
                request.getQuantity() +
                " items with id: " +
                request.getItemId());
        return ResponseEntity.ok(cartService.addItemToCart(request));
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<Cart> removeFromCart(@RequestBody ModifyCartRequest request) {
        logger.info("Received request to remove " +
                request.getQuantity() +
                " items with id: " +
                request.getItemId());
        return ResponseEntity.ok(cartService.removeItemFromCart(request));
    }
}
