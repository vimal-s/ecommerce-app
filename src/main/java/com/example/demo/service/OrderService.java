package com.example.demo.service;

import com.example.demo.EmptyOrderNotAllowedException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private OrderRepository orderRepository;
    private UserService userService;

    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public UserOrder saveOrder(String username) {
        Cart cart = userService.getUser(username).getCart();

        if (cart.getItems().isEmpty()) {
            logger.info("Order creation failed. EmptyOrderNotAllowedException occurred");
            throw new EmptyOrderNotAllowedException();
        }

        UserOrder order = UserOrder.createFromCart(cart);
        logger.info("Saving to database order of total value: " + order.getTotal());
        return orderRepository.save(order);
    }

    public List<UserOrder> getOrders(String username) {
        return orderRepository.findByUser(userService.getUser(username));
    }
}
