package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.model.Listing;
import id.ac.ui.cs.advprog.besell.model.Order;
import id.ac.ui.cs.advprog.besell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path="/order", produces="application/json")
@CrossOrigin(origins="*")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>> createOrder(@RequestBody Order order){
        Map<String, Object> res = new HashMap<>();
        return orderService.create(order)
                .thenApply(createdOrder -> {
                    res.put("order", createdOrder);
                    res.put("message", "Order Created Successfully");
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
    public CompletableFuture<ResponseEntity<Map<String, Object>>> deleteOrder(@PathVariable("id") String id) {
        Map<String, Object> res = new HashMap<>();
        return CompletableFuture.supplyAsync(() -> {
            try {
                orderService.delete(id);
                res.put("code", HttpStatus.OK.value());
                res.put("message", "Order Deleted Successfully");
                return ResponseEntity.status(HttpStatus.OK).body(res);
            } catch (Exception e) {
                res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                res.put("error", e.getMessage());
                res.put("message", "Something Wrong With Server");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
            }
        });
    }

    @PutMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>> updateOrder(@RequestBody Order order) {
        Map<String, Object> res = new HashMap<>();
        return orderService.update(order)
                .thenApply(updatedOrder -> {
                    res.put("order", updatedOrder);
                    res.put("message", "Order ID " + updatedOrder.getId() + " updated Successfully");
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
    public CompletableFuture<ResponseEntity<List<Order>>> findAllOrders(){
        return orderService.findAll()
                .thenApplyAsync(orders -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return ResponseEntity.ok(orders);
                })
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
        return orderService.findById(id)
                .thenApply(order -> {
                    if (order.isEmpty()){
                        response.put("code", HttpStatus.NOT_FOUND.value());
                        response.put("message", "Order with ID " + id + " not found.");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
                    return ResponseEntity.ok(order.get());
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
        return orderService.findBySellerId(id)
                .thenApply(order -> {
                    if (order.isEmpty()){
                        response.put("code", HttpStatus.NOT_FOUND.value());
                        response.put("message", "Listing with seller ID " + id + " not found.");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
                    return ResponseEntity.ok(order);
                })
                .exceptionally(e -> {
                    response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    response.put("error", e.getMessage());
                    response.put("message", "Something Wrong With Server");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                });
    }
}
