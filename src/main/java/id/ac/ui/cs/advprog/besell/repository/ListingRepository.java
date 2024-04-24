package id.ac.ui.cs.advprog.besell.repository;

import id.ac.ui.cs.advprog.besell.model.Listing;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ListingRepository{
    private List<Listing> listingData = new ArrayList<>();

    public Listing create(Listing listing){
        return null;
    }

    public Listing delete(Listing listing){
        return null;
    }

    public Listing update(Listing updatedListing){
        return null;
    }

    public Listing findById(String id){
        return null;
    }

    public Iterator<Listing> findAll(){
        return null;
    }
}
