package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Order;
import id.ac.ui.cs.advprog.besell.model.OrderListing;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface OrderListingService {
    CompletableFuture<OrderListing> create(OrderListing order);
    CompletableFuture<List<OrderListing>> findAll();
    CompletableFuture<Void> delete(String id);
    CompletableFuture<List<OrderListing>> findByOrderId(String id);
    CompletableFuture<OrderListing> update(OrderListing order);
}
