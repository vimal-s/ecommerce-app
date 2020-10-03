package com.example.demo.service;

import com.example.demo.EmptyOrderNotAllowedException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private UserService userService;

    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public UserOrder saveOrder(String username) {
        Cart cart = userService.getUser(username).getCart();

        if (cart.getItems().isEmpty()) {
            throw new EmptyOrderNotAllowedException();
        }

        return orderRepository.save(UserOrder.createFromCart(cart));
    }

    public List<UserOrder> getOrders(String username) {
        return orderRepository.findByUser(userService.getUser(username));
    }
}
