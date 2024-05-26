package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.OrderListing;
import id.ac.ui.cs.advprog.besell.repository.OrderListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderListingServiceImpl implements OrderListingService{

    @Autowired
    private OrderListingRepository orderListingRepository;

    @Override
    public OrderListing create(OrderListing order) {
        orderListingRepository.save(order);
        return order;
    }

    @Override
    public List<OrderListing> findAll() {
        return orderListingRepository.findAll();
    }

    @Override
    public void delete(String id) {
        orderListingRepository.deleteById(id);
    }

    @Override
    public Optional<OrderListing> findById(String id) {
        return orderListingRepository.findById(id);
    }

    @Override
    public List<OrderListing> findByOrderId(String id) {
        return orderListingRepository.findByOrderId(id);
    }

    @Override
    public OrderListing update(OrderListing order) {
        orderListingRepository.save(order);
        return order;
    }
}
