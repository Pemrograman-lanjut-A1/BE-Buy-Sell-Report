package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Listing;
import java.util.List;

public interface ListingService {
    Listing create(Listing listing);
    List<Listing> findAll();
    Listing delete(Listing listing);
    Listing findById(String id);
    Listing update(Listing listing);
}
