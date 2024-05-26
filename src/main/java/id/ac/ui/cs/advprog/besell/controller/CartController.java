package id.ac.ui.cs.advprog.besell.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import id.ac.ui.cs.advprog.besell.model.Cart;
import id.ac.ui.cs.advprog.besell.service.CartService;
import id.ac.ui.cs.advprog.besell.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/cart", produces = "application/json")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/add-item")
    public ResponseEntity<String> addItemToCart(@RequestHeader(value = "Authorization") String token, @RequestBody String cartString) throws JSONException {
        Claims claims = jwtService.resolveClaims(token);
        if (claims == null || !jwtService.validateClaims(claims)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }

        JSONObject jsonObject = new JSONObject(cartString);
        String productId = jsonObject.getString("productId");
        String productName = jsonObject.getString("productName");
        int quantity = Integer.parseInt(jsonObject.getString("quantity"));
        double price = Double.parseDouble(jsonObject.getString("price"));

        Cart cartItem = new Cart.Builder(productId, productName)
                .quantity(quantity)
                .price(price)
                .build();

        cartService.addItemToCart(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added to cart");
    }

    @GetMapping("/get-items")
    public CompletableFuture<String> getCartItems(@RequestHeader(value = "Authorization") String token) throws JsonProcessingException {
        Claims claims = jwtService.resolveClaims(token);
        if (claims == null || !jwtService.validateClaims(claims)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }

        List<Cart> cartItems = cartService.getCartItems();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String result = ow.writeValueAsString(cartItems);

        return CompletableFuture.supplyAsync(() -> result);
    }

    @PutMapping("/update-item-quantity")
    public ResponseEntity<String> updateItemQuantity(@RequestHeader(value = "Authorization") String token, @RequestParam String productId, @RequestParam int quantity) {
        Claims claims = jwtService.resolveClaims(token);
        if (claims == null || !jwtService.validateClaims(claims)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }

        cartService.updateItemQuantity(productId, quantity);
        return ResponseEntity.ok("Item quantity updated");
    }

    @DeleteMapping("/remove-items/{productId}")
    public ResponseEntity<String> removeItemFromCart(@RequestHeader(value = "Authorization") String token, @PathVariable String productId) {
        Claims claims = jwtService.resolveClaims(token);
        if (claims == null || !jwtService.validateClaims(claims)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }

        cartService.removeItemFromCart(productId);
        return ResponseEntity.ok("Item removed from cart");
    }
}
