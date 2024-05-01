package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.model.Order;
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
@RequestMapping(path="/order", produces="application/json")
@CrossOrigin(origins="*")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order){
        Map<String, Object> res = new HashMap<>();
        try{
            Order createdOrder = orderService.create(order);
            res.put("order", createdOrder);
            res.put("message", "Order Created Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }catch (Exception e){
            res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("error", e.getMessage());
            res.put("message", "Something Wrong With Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") String id){
        Map<String, Object> res = new HashMap<>();
        try{
            orderService.delete(id);
            res.put("code", HttpStatus.OK.value());
            res.put("message", "Order Deleted Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }catch (Exception e){
            res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("error", e.getMessage());
            res.put("message", "Something Wrong With Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody Order order){
        Map<String, Object> res = new HashMap<>();
        try{
            Order updatedOrder = orderService.update(order);
            res.put("order", updatedOrder);
            res.put("message", "Order ID " + updatedOrder.getId() +" updated Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }catch (Exception e){
            res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("error", e.getMessage());
            res.put("message", "Something Wrong With Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllOrders(){
        try {
            List<Order> orders = orderService.findAll();
            return ResponseEntity.ok(orders);
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
            Optional<Order> order = orderService.findById(id);
            if (order.isEmpty()){
                response.put("code", HttpStatus.NOT_FOUND.value());
                response.put("message", "Order with ID " + id + " not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(order);
        }catch (Exception e){
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", e.getMessage());
            response.put("message", "Something Wrong With Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
