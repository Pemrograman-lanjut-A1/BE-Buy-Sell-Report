package id.ac.ui.cs.advprog.besell.repository;

import id.ac.ui.cs.advprog.besell.model.OrderListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderListingRepository extends JpaRepository<OrderListing, String> {
    List<OrderListing> findByOrderId(String id);
}
