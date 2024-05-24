package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface OrderService {
    CompletableFuture<Order> create(Order order);
    CompletableFuture<List<Order>> findAll();
    CompletableFuture<Void> delete(String id);
    CompletableFuture<Optional<Order>> findById(String id);
    CompletableFuture<Order> update(Order order);
    CompletableFuture<List<Order>> findBySellerId(String id);
}
