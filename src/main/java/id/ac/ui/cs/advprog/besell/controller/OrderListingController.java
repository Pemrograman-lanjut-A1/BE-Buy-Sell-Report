package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.model.OrderListing;
import id.ac.ui.cs.advprog.besell.service.OrderListingService;
import id.ac.ui.cs.advprog.besell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path="/order-listing", produces="application/json")
@CrossOrigin(origins="*")
public class OrderListingController {

    @Autowired
    OrderListingService orderListingService;

    @PostMapping
    public ResponseEntity<?> createOrderListing(@RequestBody OrderListing orderListing){
        Map<String, Object> res = new HashMap<>();
        try{
            OrderListing createdOrderListing = orderListingService.create(orderListing);
            res.put("orderListing", createdOrderListing);
            res.put("message", "OrderListing Created Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }catch (Exception e){
            res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("error", e.getMessage());
            res.put("message", "Something Wrong With Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderListing(@PathVariable("id") String id){
        Map<String, Object> res = new HashMap<>();
        try{
            orderListingService.delete(id);
            res.put("code", HttpStatus.OK.value());
            res.put("message", "OrderListing Deleted Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }catch (Exception e){
            res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("error", e.getMessage());
            res.put("message", "Something Wrong With Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateOrderListing(@RequestBody OrderListing orderListing){
        Map<String, Object> res = new HashMap<>();
        try{
            OrderListing updatedOrderListing = orderListingService.update(orderListing);
            res.put("orderListing", updatedOrderListing);
            res.put("message", "OrderListing ID " + updatedOrderListing.getOrderId() +" updated Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }catch (Exception e){
            res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("error", e.getMessage());
            res.put("message", "Something Wrong With Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllOrderListings(){
        try {
            List<OrderListing> orderListings = orderListingService.findAll();
            return ResponseEntity.ok(orderListings);
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
            Optional<OrderListing> orderListing = orderListingService.findById(id);
            if (orderListing.isEmpty()){
                response.put("code", HttpStatus.NOT_FOUND.value());
                response.put("message", "OrderListing with ID " + id + " not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(orderListing);
        }catch (Exception e){
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", e.getMessage());
            response.put("message", "Something Wrong With Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
