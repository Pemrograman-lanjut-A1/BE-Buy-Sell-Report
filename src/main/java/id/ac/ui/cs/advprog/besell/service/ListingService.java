package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Listing;
import java.util.List;
import java.util.Optional;

public interface ListingService {
    Listing create(Listing listing);
    List<Listing> findAll();
    void delete(String id);
    Optional<Listing> findById(String id);
    Listing update(Listing listing);
}
