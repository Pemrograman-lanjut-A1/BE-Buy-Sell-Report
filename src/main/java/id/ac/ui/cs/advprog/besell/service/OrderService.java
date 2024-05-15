package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order create(Order order);
    List<Order> findAll();
    void delete(String id);
    Optional<Order> findById(String id);
    Order update(Order order);
}
