package id.ac.ui.cs.advprog.besell.controller;
import id.ac.ui.cs.advprog.besell.config.JwtAuthFilter;
import id.ac.ui.cs.advprog.besell.model.Listing;
import id.ac.ui.cs.advprog.besell.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.ExpiredJwtException;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path="/listing", produces="application/json")
@CrossOrigin(origins={"http://localhost:8080", "http://localhost:3000", "https://fe-repo-inky.vercel.app"})
public class ListingController {
    private static final String MESSAGE_KEY = "message";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Something Wrong With Server";
    private static final String EXPIRED_JWT_MESSAGE = "JWT token has expired";
    private static final String INVALID_JWT_MESSAGE = "Invalid JWT token";
    private static final String FORBIDDEN_MESSAGE = "You are not authorized to make this request";
    private static final String ERROR_KEY_MESSAGE = "error";
    private final ListingService listingService;
    private final JwtAuthFilter jwtAuthFilter;

    @Autowired
    public ListingController(ListingService listingService, JwtAuthFilter jwtAuthFilter){
        this.jwtAuthFilter = jwtAuthFilter;
        this.listingService = listingService;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>> createListing(
            @RequestHeader(value = "Authorization") String token, @RequestBody Listing listing){
        Map<String, Object> res = new HashMap<>();

        String role = null;
        try {
            role = jwtAuthFilter.filterToken(token);
        }catch (Exception e){
            handleJwtException(e);
        }

        if (role == null){
            Map<String, Object> forbiddenResponse = handleForbidden();
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body(forbiddenResponse));
        }

        return listingService.create(listing)
                .thenApply(createdListing -> {
                    res.put("listing", createdListing);
                    res.put(MESSAGE_KEY, "Listing Created Successfully");
                    return ResponseEntity.status(HttpStatus.CREATED).body(res);
                })
                .exceptionally(e -> {
                    Map<String, Object> errorResponse = handleInternalError((Exception) e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                });
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> deleteListing(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable("id") String id){
        Map<String, Object> res = new HashMap<>();

        String role = null;
        try {
            role = jwtAuthFilter.filterToken(token);
        }catch (Exception e){
            handleJwtException(e);
        }

        if (role == null){
            Map<String, Object> forbiddenResponse = handleForbidden();
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body(forbiddenResponse));
        }

        return listingService.delete(id)
                .thenApply(result -> {
                    res.put("code", HttpStatus.OK.value());
                    res.put(MESSAGE_KEY, "Listing Deleted Successfully");
                    return ResponseEntity.status(HttpStatus.OK).body(res);
                })
                .exceptionally(e -> {
                    Map<String, Object> errorResponse = handleInternalError((Exception) e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                });
    }

    @PutMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>> updateListing(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody Listing listing){
        Map<String, Object> res = new HashMap<>();

        String role = null;
        try {
            role = jwtAuthFilter.filterToken(token);
        }catch (Exception e){
            handleJwtException(e);
        }

        if (role == null){
            Map<String, Object> forbiddenResponse = handleForbidden();
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body(forbiddenResponse));
        }

        return listingService.update(listing)
                .thenApply(updatedListing -> {
                    res.put("listing", updatedListing);
                    res.put(MESSAGE_KEY, "Listing ID " + updatedListing.getId() +" updated Successfully");
                    return ResponseEntity.status(HttpStatus.CREATED).body(res);
                })
                .exceptionally(e -> {
                    Map<String, Object> errorResponse = handleInternalError((Exception) e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                });
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Listing>>> findAllListings(){
        return listingService.findAll()
                .thenApplyAsync(ResponseEntity::ok)
                .exceptionally(exception -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    response.put(ERROR_KEY_MESSAGE, exception.getCause() != null ? exception.getCause().getMessage() : "Unknown error");
                    response.put(MESSAGE_KEY, "Something went wrong with the server");
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
                        response.put(MESSAGE_KEY, "Listing with ID " + id + " not found.");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
                    return ResponseEntity.ok(listing.get());
                })
                .exceptionally(e -> {
                    Map<String, Object> errorResponse = handleInternalError((Exception) e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                });
    }

    @GetMapping("/seller/{id}")
    public CompletableFuture<ResponseEntity<?>> findBySellerId(@PathVariable("id") String id){
        Map<String, Object> response = new HashMap<>();
        return listingService.findBySellerId(id)
                .thenApply(listing -> {
                    if (listing.isEmpty()){
                        response.put("code", HttpStatus.NOT_FOUND.value());
                        response.put(MESSAGE_KEY, "Listing with seller ID " + id + " not found.");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
                    return ResponseEntity.ok(listing);
                })
                .exceptionally(e -> {
                    Map<String, Object> errorResponse = handleInternalError((Exception) e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                });
    }

    private Map<String, Object> handleJwtException(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", HttpStatus.FORBIDDEN.value());
        response.put(MESSAGE_KEY, e instanceof ExpiredJwtException ? EXPIRED_JWT_MESSAGE : INVALID_JWT_MESSAGE);
        return response;
    }

    private Map<String, Object> handleInternalError(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put(ERROR_KEY_MESSAGE, e.getMessage());
        response.put(MESSAGE_KEY, INTERNAL_SERVER_ERROR_MESSAGE);
        return response;
    }
    private Map<String, Object> handleForbidden() {
        Map<String, Object> response = new HashMap<>();
        response.put("code", HttpStatus.FORBIDDEN.value());
        response.put(MESSAGE_KEY, FORBIDDEN_MESSAGE);
        return response;
    }
}