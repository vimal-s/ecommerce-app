package com.example.demo.service;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CartRepository cartRepository;
    private ItemService itemService;
    private UserService userService;

    public CartService(
            CartRepository cartRepository,
            ItemService itemService,
            UserService userService) {
        this.cartRepository = cartRepository;
        this.itemService = itemService;
        this.userService = userService;
    }

    public Cart save(Cart cart) {
        logger.info("Saving to database cart with id: " + cart.getId());
        return cartRepository.save(cart);
    }

    public Cart addItemToCart(ModifyCartRequest cartRequest) {
        Cart cart = userService.getUser(cartRequest.getUsername()).getCart();
        Item item = itemService.getItem(cartRequest.getItemId());

        logger.info("Adding to cart with id: " + cart.getId() + ", items of total value: " + cart.getTotal());
        modifyCart(cartRequest.getQuantity(), index -> cart.addItem(item));
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(ModifyCartRequest cartRequest) {
        Cart cart = userService.getUser(cartRequest.getUsername()).getCart();
        Item item = itemService.getItem(cartRequest.getItemId());

        logger.info("Removing from cart with id: " + cart.getId() + ", items of total value: " + cart.getTotal());
        modifyCart(cartRequest.getQuantity(), index -> cart.removeItem(item));
        return cartRepository.save(cart);
    }

    private void modifyCart(int itemQuantity, IntConsumer itemConsumer) {
        IntStream.range(0, itemQuantity).forEach(itemConsumer);
    }
}
