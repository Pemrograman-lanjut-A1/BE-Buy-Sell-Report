package id.ac.ui.cs.advprog.besell.controller;
import id.ac.ui.cs.advprog.besell.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/listing", produces="application/json")
@CrossOrigin(origins="*")
public class ListingController {

    @Autowired
    ListingService listingService;

    @GetMapping("/create")
    public String createListing(){
        return "CreateListing";
    }

    @GetMapping("/delete")
    public String deleteListing(){
        return "DeleteListing";
    }

    @GetMapping("/update")
    public String updateListing(){
        return "UpdateListing";
    }

    @GetMapping("/all")
    public String findAllListings(){
        return "ListingList";
    }
}