package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.OrderListing;
import id.ac.ui.cs.advprog.besell.repository.OrderListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderListingServiceImpl implements OrderListingService{

    @Autowired
    private OrderListingRepository orderListingRepository;

    @Override
    public CompletableFuture<OrderListing> create(OrderListing order) {
        orderListingRepository.save(order);
        return CompletableFuture.completedFuture(order);
    }

    @Override
    public CompletableFuture<List<OrderListing>> findAll() {
        return CompletableFuture.completedFuture(orderListingRepository.findAll());
    }

    @Override
    public CompletableFuture<Void> delete(String id) {
        orderListingRepository.deleteById(id);
        return null;
    }

    @Override
    public CompletableFuture<List<OrderListing>> findByOrderId(String id) {
        return CompletableFuture.completedFuture(orderListingRepository.findByOrderId(id));
    }

    @Override
    public CompletableFuture<OrderListing> update(OrderListing order) {
        orderListingRepository.save(order);
        return CompletableFuture.completedFuture(order);
    }
}
