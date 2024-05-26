package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.config.JwtAuthFilter;
import id.ac.ui.cs.advprog.besell.model.OrderListing;
import id.ac.ui.cs.advprog.besell.service.OrderListingService;
import id.ac.ui.cs.advprog.besell.service.OrderService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path="/order-listing", produces="application/json")
@CrossOrigin(origins="*")
public class OrderListingController {
    private static final String MESSAGE_KEY = "message";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Something Wrong With Server";
    private static final String EXPIRED_JWT_MESSAGE = "JWT token has expired";
    private static final String INVALID_JWT_MESSAGE = "Invalid JWT token";
    private static final String FORBIDDEN_MESSAGE = "You are not authorized to make this request";
    private static final String ERROR_KEY_MESSAGE = "error";
    private final OrderListingService orderListingService;
    private final JwtAuthFilter jwtAuthFilter;

    @Autowired
    public OrderListingController(OrderListingService orderListingService, JwtAuthFilter jwtAuthFilter){
        this.jwtAuthFilter = jwtAuthFilter;
        this.orderListingService = orderListingService;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>> createOrderListing(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody OrderListing orderListing){
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

        return orderListingService.create(orderListing)
                .thenApply(createdOrderListing -> {
                    res.put("orderListing", createdOrderListing);
                    res.put(MESSAGE_KEY, "OrderListing Created Successfully");
                    return ResponseEntity.status(HttpStatus.CREATED).body(res);
                })
                .exceptionally(e -> {
                    Map<String, Object> errorResponse = handleInternalError((Exception) e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                });
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> deleteOrderListing(
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

        return CompletableFuture.supplyAsync(() -> {
            try {
                orderListingService.delete(id);
                res.put("code", HttpStatus.OK.value());
                res.put(MESSAGE_KEY, "OrderListing Deleted Successfully");
                return ResponseEntity.status(HttpStatus.OK).body(res);
            } catch (Exception e) {
                Map<String, Object> errorResponse = handleInternalError(e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        });
    }

    @PutMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>> updateOrderListing(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody OrderListing orderListing){
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

        return orderListingService.update(orderListing)
                .thenApply(updatedOrderListing -> {
                    res.put("orderListing", updatedOrderListing);
                    res.put(MESSAGE_KEY, "OrderListing ID " + updatedOrderListing.getId() + " updated Successfully");
                    return ResponseEntity.status(HttpStatus.CREATED).body(res);
                })
                .exceptionally(e -> {
                    Map<String, Object> errorResponse = handleInternalError((Exception) e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                });
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<OrderListing>>> findAllOrderListings(){
        return orderListingService.findAll()
                .thenApplyAsync(orders -> ResponseEntity.ok(orders))
                .exceptionally(exception -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    response.put(ERROR_KEY_MESSAGE, exception.getCause() != null ? exception.getCause().getMessage() : "Unknown error");
                    response.put(MESSAGE_KEY, "Something went wrong with the server");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
                });
    }

    @GetMapping("/order/{id}")
    public CompletableFuture<ResponseEntity<?>> findByOrderId(@PathVariable("id") String id){
        Map<String, Object> response = new HashMap<>();
        return orderListingService.findByOrderId(id)
                .thenApply(orderListing -> {
                    if (orderListing.isEmpty()){
                        response.put("code", HttpStatus.NOT_FOUND.value());
                        response.put(MESSAGE_KEY, "Listing with order ID " + id + " not found.");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
                    return ResponseEntity.ok(orderListing);
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
