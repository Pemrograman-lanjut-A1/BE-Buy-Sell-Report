package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Order;
import id.ac.ui.cs.advprog.besell.model.OrderListing;

import java.util.List;
import java.util.Optional;

public interface OrderListingService {
    OrderListing create(OrderListing order);
    List<OrderListing> findAll();
    void delete(String id);
    Optional<OrderListing> findById(String id);
    OrderListing update(OrderListing order);
}
