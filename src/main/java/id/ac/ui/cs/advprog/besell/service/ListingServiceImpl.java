package id.ac.ui.cs.advprog.besell.service;


import id.ac.ui.cs.advprog.besell.model.Listing;
import id.ac.ui.cs.advprog.besell.repository.ListingRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;

    public ListingServiceImpl(ListingRepository listingRepository){
        this.listingRepository = listingRepository;
    }

    @Override
    @Async
    public CompletableFuture<Listing> create(Listing listing){
        listingRepository.save(listing);
        return CompletableFuture.completedFuture(listing);
    }

    @Override
    @Async
    public CompletableFuture<List<Listing>> findAll(){
        return CompletableFuture.completedFuture(listingRepository.findAll());
    }

    @Override
    @Async
    public CompletableFuture<Void> delete(String id){
        listingRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async
    public CompletableFuture<Listing> update(Listing listing){
        listingRepository.save(listing);
        return CompletableFuture.completedFuture(listing);
    }

    @Override
    @Async
    public CompletableFuture<Optional<Listing>> findById(String id){

        return CompletableFuture.completedFuture(listingRepository.findById(id));
    }
    @Override
    @Async
    public CompletableFuture<List<Listing>> findBySellerId(String id){
        return CompletableFuture.completedFuture(listingRepository.findBySellerId(id));
    }
}
