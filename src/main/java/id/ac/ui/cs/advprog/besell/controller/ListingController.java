package id.ac.ui.cs.advprog.besell.controller;
import id.ac.ui.cs.advprog.besell.model.Listing;
import id.ac.ui.cs.advprog.besell.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path="/listing", produces="application/json")
@CrossOrigin(origins="*")
public class ListingController {

    @Autowired
    ListingService listingService;

    @PostMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>> createListing(@RequestBody Listing listing){
        Map<String, Object> res = new HashMap<>();
        return listingService.create(listing)
                .thenApply(createdListing -> {
                    res.put("listing", createdListing);
                    res.put("message", "Listing Created Successfully");
                    return ResponseEntity.status(HttpStatus.CREATED).body(res);
                })
                .exceptionally(e -> {
                    res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    res.put("error", e.getMessage());
                    res.put("message", "Something Wrong With Server");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
                });
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> deleteListing(@PathVariable("id") String id){
        Map<String, Object> res = new HashMap<>();
        return listingService.delete(id)
                .thenApply(result -> {
                    res.put("code", HttpStatus.OK.value());
                    res.put("message", "Listing Deleted Successfully");
                    return ResponseEntity.status(HttpStatus.OK).body(res);
                })
                .exceptionally(e -> {
                    res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    res.put("error", e.getMessage());
                    res.put("message", "Something Wrong With Server");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
                });
    }

    @PutMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>> updateListing(@RequestBody Listing listing){
        Map<String, Object> res = new HashMap<>();
        return listingService.update(listing)
                .thenApply(updatedListing -> {
                    res.put("listing", updatedListing);
                    res.put("message", "Listing ID " + updatedListing.getId() +" updated Successfully");
                    return ResponseEntity.status(HttpStatus.CREATED).body(res);
                })
                .exceptionally(e -> {
                    res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    res.put("error", e.getMessage());
                    res.put("message", "Something Wrong With Server");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
                });
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Listing>>> findAllListings(){
        return listingService.findAll()
                .thenApplyAsync(listings -> ResponseEntity.ok(listings))
                .exceptionally(exception -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    response.put("error", exception.getCause() != null ? exception.getCause().getMessage() : "Unknown error");
                    response.put("message", "Something went wrong with the server");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
                });
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> findById(@PathVariable("id") String id){
        Map<String, Object> response = new HashMap<>();
        return listingService.findById(id)
                .thenApply(listing -> {
                    if (listing.isEmpty()){
                        response.put("code", HttpStatus.NOT_FOUND.value());
                        response.put("message", "Listing with ID " + id + " not found.");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
                    return ResponseEntity.ok(listing.get());
                })
                .exceptionally(e -> {
                    response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    response.put("error", e.getMessage());
                    response.put("message", "Something Wrong With Server");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                });
    }

    @GetMapping("/seller/{id}")
    public CompletableFuture<ResponseEntity<?>> findBySellerId(@PathVariable("id") String id){
        Map<String, Object> response = new HashMap<>();
        return listingService.findBySellerId(id)
                .thenApply(listing -> {
                    if (listing.isEmpty()){
                        response.put("code", HttpStatus.NOT_FOUND.value());
                        response.put("message", "Listing with ID " + id + " not found.");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
                    return ResponseEntity.ok(listing);
                })
                .exceptionally(e -> {
                    response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    response.put("error", e.getMessage());
                    response.put("message", "Something Wrong With Server");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                });
    }
}