package id.ac.ui.cs.advprog.besell.model;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter @Setter
public class Listing {
    private String listingId;
    private String listingName;
    private int listingPrice;
    private int listingStock;

    public Listing(){
        this.listingName = UUID.randomUUID().toString();
    }
}
