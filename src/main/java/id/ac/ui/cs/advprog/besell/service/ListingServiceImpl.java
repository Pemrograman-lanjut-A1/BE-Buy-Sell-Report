package id.ac.ui.cs.advprog.besell.service;


import id.ac.ui.cs.advprog.besell.model.Listing;
import id.ac.ui.cs.advprog.besell.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ListingServiceImpl implements ListingService {
    @Autowired
    private ListingRepository listingRepository;

    @Override
    public Listing create(Listing listing){
        listingRepository.save(listing);
        return listing;
    }

    @Override
    public List<Listing> findAll(){
//        Iterator<Listing> listingIterator = listingRepository.findAll();
//        List<Listing> allListing = new ArrayList<>();
//        listingIterator.forEachRemaining(allListing::add);
        return listingRepository.findAll();
    }

    @Override
    public void delete(String id){
        listingRepository.deleteById(id);
    }

    @Override
    public Listing update(Listing listing){
        listingRepository.save(listing);
        return listing;
    }

    @Override
    public Optional<Listing> findById(String id){
        return listingRepository.findById(id);
    }
}
