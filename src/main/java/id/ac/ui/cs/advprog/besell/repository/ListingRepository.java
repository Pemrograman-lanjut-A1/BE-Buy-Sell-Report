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
        if(listing.getStock() < 0){
            listing.setStock(0);
        }
        listingData.add(listing);
        return listing;
    }

    public Listing delete(Listing listing){
        listingData.remove(listing);
        return listing;
    }

    public Listing update(Listing updatedListing){
        if(updatedListing.getStock() < 0){
            updatedListing.setStock(0);
        }
        for (int i = 0; i < listingData.size(); i++) {
            Listing listing = listingData.get(i);
            if(listing.getId().equals(updatedListing.getId())){
                listingData.set(i, updatedListing);
                return updatedListing;
            }
        }
        return null;
    }

    public Listing findById(String id){
        for(Listing listing : listingData){
            if(listing.getId().equals(id)){
                return listing;
            }
        }
        return null;
    }

    public Iterator<Listing> findAll(){
        return listingData.iterator();
    }
}
