package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Order;
import id.ac.ui.cs.advprog.besell.repository.OrderRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    @Async
    public CompletableFuture<Order> create(Order order) {
        orderRepository.save(order);
        return CompletableFuture.completedFuture(order);
    }

    @Override
    @Async
    public CompletableFuture<List<Order>> findAll() {
        return CompletableFuture.completedFuture(orderRepository.findAll());
    }

    @Override
    @Async
    public CompletableFuture<Void> delete(String id) {
        orderRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async
    public CompletableFuture<Optional<Order>> findById(String id) {
        return CompletableFuture.completedFuture(orderRepository.findById(id));
    }

    @Override
    public CompletableFuture<Order> update(Order order) {
        orderRepository.save(order);
        return CompletableFuture.completedFuture(order);
    }

    @Override
    @Async
    public CompletableFuture<List<Order>> findBySellerId(String id){
        return CompletableFuture.completedFuture(orderRepository.findBySellerId(id));
    }
}
