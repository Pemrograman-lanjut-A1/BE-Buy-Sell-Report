package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.config.JwtAuthFilter;
import id.ac.ui.cs.advprog.besell.model.Order;
import id.ac.ui.cs.advprog.besell.service.OrderService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path="/order", produces="application/json")
@CrossOrigin(origins={"http://localhost:8080","http://localhost:3000", "https://fe-repo-inky.vercel.app"})
public class OrderController {
    private static final String MESSAGE_KEY = "message";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Something Wrong With Server";
    private static final String EXPIRED_JWT_MESSAGE = "JWT token has expired";
    private static final String INVALID_JWT_MESSAGE = "Invalid JWT token";
    private static final String FORBIDDEN_MESSAGE = "You are not authorized to make this request";
    private static final String ERROR_KEY_MESSAGE = "error";
    private final OrderService orderService;
    private final JwtAuthFilter jwtAuthFilter;

    @Autowired
    public OrderController(OrderService orderService, JwtAuthFilter jwtAuthFilter){
        this.jwtAuthFilter = jwtAuthFilter;
        this.orderService = orderService;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>> createOrder(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody Order order){
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

        return orderService.create(order)
                .thenApply(createdOrder -> {
                    res.put("order", createdOrder);
                    res.put(MESSAGE_KEY, "Order Created Successfully");
                    return ResponseEntity.status(HttpStatus.CREATED).body(res);
                })
                .exceptionally(e -> {
                    Map<String, Object> errorResponse = handleInternalError((Exception) e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                });
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> deleteOrder(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable("id") String id) {
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
                orderService.delete(id);
                res.put("code", HttpStatus.OK.value());
                res.put(MESSAGE_KEY, "Order Deleted Successfully");
                return ResponseEntity.status(HttpStatus.OK).body(res);
            } catch (Exception e) {
                Map<String, Object> errorResponse = handleInternalError(e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        });
    }

    @PutMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>> updateOrder(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody Order order) {
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

        return orderService.update(order)
                .thenApply(updatedOrder -> {
                    res.put("order", updatedOrder);
                    res.put(MESSAGE_KEY, "Order ID " + updatedOrder.getId() + " updated Successfully");
                    return ResponseEntity.status(HttpStatus.CREATED).body(res);
                })
                .exceptionally(e -> {
                    Map<String, Object> errorResponse = handleInternalError((Exception) e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                });
    }



    @GetMapping
    public CompletableFuture<ResponseEntity<List<Order>>> findAllOrders(){
        return orderService.findAll()
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
        return orderService.findById(id)
                .thenApply(order -> {
                    if (order.isEmpty()){
                        response.put("code", HttpStatus.NOT_FOUND.value());
                        response.put(MESSAGE_KEY, "Order with ID " + id + " not found.");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
                    return ResponseEntity.ok(order.get());
                })
                .exceptionally(e -> {
                    Map<String, Object> errorResponse = handleInternalError((Exception) e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                });
    }

    @GetMapping("/seller/{id}")
    public CompletableFuture<ResponseEntity<?>> findBySellerId(@PathVariable("id") String id){
        Map<String, Object> response = new HashMap<>();
        return orderService.findBySellerId(id)
                .thenApply(order -> {
                    if (order.isEmpty()){
                        response.put("code", HttpStatus.NOT_FOUND.value());
                        response.put(MESSAGE_KEY, "Listing with seller ID " + id + " not found.");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
                    return ResponseEntity.ok(order);
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
