package id.ac.ui.cs.advprog.besell.controller;
import id.ac.ui.cs.advprog.besell.model.Listing;
import id.ac.ui.cs.advprog.besell.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path="/listing", produces="application/json")
@CrossOrigin(origins="*")
public class ListingController {

    @Autowired
    ListingService listingService;

    @PostMapping
    public ResponseEntity<?> createListing(@RequestBody Listing listing){
        Map<String, Object> res = new HashMap<>();
        try{
            Listing createdListing = listingService.create(listing);
            res.put("listing", createdListing);
            res.put("message", "Listing Created Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }catch (Exception e){
            res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("error", e.getMessage());
            res.put("message", "Something Wrong With Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteListing(@PathVariable("id") String id){
        Map<String, Object> res = new HashMap<>();
        try{
            listingService.delete(id);
            res.put("code", HttpStatus.OK.value());
            res.put("message", "Listing Deleted Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }catch (Exception e){
            res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("error", e.getMessage());
            res.put("message", "Something Wrong With Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateListing(@RequestBody Listing listing){
        Map<String, Object> res = new HashMap<>();
        try{
            Listing updatedListing = listingService.update(listing);
            res.put("listing", updatedListing);
            res.put("message", "Listing ID " + updatedListing.getId() +" updated Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }catch (Exception e){
            res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("error", e.getMessage());
            res.put("message", "Something Wrong With Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllListings(){
        try {
            List<Listing> listings = listingService.findAll();
            return ResponseEntity.ok(listings);
        }catch (Exception e){
            Map<String, Object> response = new HashMap<>();
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", e.getMessage());
            response.put("message", "Something Wrong With Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id){
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Listing> listing = listingService.findById(id);
            if (listing.isEmpty()){
                response.put("code", HttpStatus.NOT_FOUND.value());
                response.put("message", "Listing with ID " + id + " not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(listing);
        }catch (Exception e){
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", e.getMessage());
            response.put("message", "Something Wrong With Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}