package id.ac.ui.cs.advprog.besell.repository;


import id.ac.ui.cs.advprog.besell.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findBySellerId(String sellerId);
}
