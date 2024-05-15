package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Listing;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ListingService {
    CompletableFuture<Listing> create(Listing listing);
    CompletableFuture<List<Listing>> findAll();
    CompletableFuture<Void> delete(String id);
    CompletableFuture<Optional<Listing>> findById(String id);
    CompletableFuture<List<Listing>> findBySellerId(String id);
    CompletableFuture<Listing> update(Listing listing);
}
