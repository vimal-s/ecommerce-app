package com.example.demo.service;

import com.example.demo.ItemNotFoundException;
import com.example.demo.UserNotFoundException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
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
    private ItemRepository itemRepository;
    private UserRepository userRepository;

    public CartService(
            CartRepository cartRepository,
            ItemRepository itemRepository,
            UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart addItemToCart(ModifyCartRequest cartRequest) {
        Cart cart = getUser(cartRequest).getCart();
        Item item = getItem(cartRequest);
        modifyCart(cartRequest.getQuantity(), index -> cart.addItem(item));
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(ModifyCartRequest cartRequest) {
        Cart cart = getUser(cartRequest).getCart();
        Item item = getItem(cartRequest);
        modifyCart(cartRequest.getQuantity(), index -> cart.removeItem(item));
        return cartRepository.save(cart);
    }

    private User getUser(ModifyCartRequest cartRequest) {
        User user = userRepository.findByUsername(cartRequest.getUsername());
        if (user == null) {
            throw new UserNotFoundException(cartRequest.getUsername());
        }
        return user;
    }

    private Item getItem(ModifyCartRequest cartRequest) {
        return itemRepository
                .findById(cartRequest.getItemId())
                .orElseThrow(() -> new ItemNotFoundException(cartRequest.getItemId()));
    }

    private void modifyCart(int itemQuantity, IntConsumer itemConsumer) {
        IntStream.range(0, itemQuantity).forEach(itemConsumer);
    }
}
