package id.ac.ui.cs.advprog.besell.repository;

import id.ac.ui.cs.advprog.besell.model.OrderListing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderListingRepository extends JpaRepository<OrderListing, String> {
}
