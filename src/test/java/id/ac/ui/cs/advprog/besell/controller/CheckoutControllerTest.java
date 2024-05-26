package id.ac.ui.cs.advprog.besell.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.besell.model.Checkout;
import id.ac.ui.cs.advprog.besell.service.CheckoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckoutControllerTest {

    @Mock
    private CheckoutService checkoutService;

    @Mock
    private CartController cartController;

    @InjectMocks
    private CheckoutController checkoutController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Checkout checkout;

    @BeforeEach
    void setUp() {
        checkout = new Checkout();
        checkout.setShippingAddress("123 Main St");
        checkout.setPaymentMethod("Credit Card");
    }

//    @Test
//    void processCheckout_WithOKResponse() throws JsonProcessingException {
//        List<Cart> mockCartItems = Collections.singletonList(new Cart());
//        ResponseEntity<String> okResponseEntity = ResponseEntity.ok(objectMapper.writeValueAsString(mockCartItems));
//
//        when(cartController.getCartItems(token)).thenReturn(CompletableFuture.completedFuture(objectMapper.writeValueAsString(okResponseEntity)));
//        when(checkoutService.processCheckout(anyList(), anyString(), anyString())).thenReturn(CompletableFuture.completedFuture(ResponseEntity.ok().build()));
//
//        CompletableFuture<ResponseEntity<Checkout>> result = checkoutController.processCheckout(checkout, token);
//
//        assertEquals(HttpStatus.OK, result.join().getStatusCode());
//        verify(cartController, times(1)).getCartItems(token);
//        verify(checkoutService, times(1)).processCheckout(mockCartItems, "123 Main St", "Credit Card");
//    }

    @Test
    void processCheckout_WithErrorResponse() throws JsonProcessingException {
        ResponseEntity<String> errorResponseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");

        String token = "fake-token";
        when(cartController.getCartItems(token)).thenReturn(CompletableFuture.completedFuture(objectMapper.writeValueAsString(errorResponseEntity)));

        CompletableFuture<ResponseEntity<Checkout>> result = checkoutController.processCheckout(checkout, token);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.join().getStatusCode());
        verify(cartController, times(1)).getCartItems(token);
        verify(checkoutService, never()).processCheckout(anyList(), anyString(), anyString());
    }
}
