package id.ac.ui.cs.advprog.besell.service;


import id.ac.ui.cs.advprog.besell.model.Listing;
import id.ac.ui.cs.advprog.besell.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ListingServiceImpl implements ListingService {
    @Autowired
    private ListingRepository listingRepository;

    @Override
    public Listing create(Listing listing){
        return listing;
    }

    @Override
    public List<Listing> findAll(){
        return null;
    }

    @Override
    public Listing delete(Listing listing){
        listingRepository.delete(listing);
        return listing;
    }

    @Override
    public Listing update(Listing listing){
        listingRepository.update(listing);
        return listing;
    }

    @Override
    public Listing findById(String id){
        return listingRepository.findById(id);
    }
}
