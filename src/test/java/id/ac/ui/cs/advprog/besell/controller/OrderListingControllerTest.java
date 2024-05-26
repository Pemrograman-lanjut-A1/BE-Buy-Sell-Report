package id.ac.ui.cs.advprog.besell.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.*;

import id.ac.ui.cs.advprog.besell.config.JwtAuthFilter;
import id.ac.ui.cs.advprog.besell.model.OrderListing;
import id.ac.ui.cs.advprog.besell.service.OrderListingService;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class OrderListingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderListingService OrderListingService;

    @Mock
    private JwtAuthFilter jwtAuthFilter;

    @InjectMocks
    private OrderListingController OrderListingController;

    public OrderListingControllerTest() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(OrderListingController).build();
    }

    @Test
    void testFindAllOrderListings() throws Exception {
        // Mocking the service response
        List<OrderListing> OrderListings = Arrays.asList(new OrderListing(), new OrderListing());
        CompletableFuture<List<OrderListing>> future = CompletableFuture.completedFuture(OrderListings);
        when(OrderListingService.findAll()).thenReturn(future);

        // Perform GET request
        MvcResult result = mockMvc.perform(get("/order-listing"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();


        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    void findAllInternalError() throws Exception {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(OrderListingService.findAll()).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<List<OrderListing>> responseEntity =
                OrderListingController.findAllOrderListings().get();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testFindByOrderId() throws Exception {
        // Mocking the service response
        OrderListing OrderListing = new OrderListing();
        OrderListing.setOrderId("123");
        CompletableFuture<List<OrderListing>> future = CompletableFuture.completedFuture(List.of(OrderListing));
        when(OrderListingService.findByOrderId(anyString())).thenReturn(future);

        MvcResult result = mockMvc.perform(get("/order-listing/order/123"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value("123"));
    }

    @Test
    void findByOrderIdInternalError() throws Exception {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(OrderListingService.findByOrderId(any(String.class))).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<?> responseEntity =
                OrderListingController.findByOrderId("fakeId").get();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testFindByOrderId_NotFound() throws Exception {
        // Mocking the service response
        CompletableFuture<List<OrderListing>> future = CompletableFuture.completedFuture(Collections.emptyList());
        when(OrderListingService.findByOrderId(anyString())).thenReturn(future);

        MvcResult result = mockMvc.perform(get("/order-listing/order/123"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateOrderListing() throws Exception {
        // Mocking the service response
        OrderListing OrderListing = new OrderListing();
        OrderListing.setOrderId("123");
        CompletableFuture<OrderListing> future = CompletableFuture.completedFuture(OrderListing);
        when(OrderListingService.create(any(OrderListing.class))).thenReturn(future);
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");

        // Perform POST request
        MvcResult result = mockMvc.perform(post("/order-listing")
                        .contentType("application/json")
                        .header("Authorization", "mockToken")
                        .content("{\"orderId\":\"123\",\"listingId\":\"321\"}"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderListing.orderId").value("123"))
                .andExpect(jsonPath("$.message").value("OrderListing Created Successfully"));
    }

    @Test
    void createOrderListingInternalError() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(OrderListingService.create(any(OrderListing.class))).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<Map<String, Object>> responseEntity =
                OrderListingController.createOrderListing("mockedToken", new OrderListing()).join();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void createOrderListingExpiredJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenThrow(ExpiredJwtException.class);

        ResponseEntity<Map<String, Object>> responseEntity =
                OrderListingController.createOrderListing("mockedToken", new OrderListing()).join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    void createOrderListingEmptyJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn(null);

        ResponseEntity<Map<String, Object>> responseEntity =
                OrderListingController.createOrderListing("mockedToken", new OrderListing()).join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("You are not authorized to make this request", responseEntity.getBody().get("message"));
    }

    @Test
    void testDeleteOrderListing() throws Exception {
        // Mocking the service response
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(OrderListingService.delete("123")).thenReturn(future);

        // Perform DELETE request
        MvcResult result = mockMvc.perform(delete("/order-listing/123").header("Authorization", "mockToken"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("OrderListing Deleted Successfully"));
    }

    @Test
    void deleteOrderListingInternalError() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(OrderListingService.delete(any(String.class))).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<?> responseEntity =
                OrderListingController.deleteOrderListing("mockedToken", "mockId").join();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void deleteOrderListingExpiredJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenThrow(ExpiredJwtException.class);

        ResponseEntity<?> responseEntity =
                OrderListingController.deleteOrderListing("mockedToken", "mockId").join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    void deleteOrderListingEmptyJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn(null);

        ResponseEntity<?> responseEntity =
                OrderListingController.deleteOrderListing("mockedToken", "mockId").join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
//        assertEquals("You are not authorized to make this request", responseEntity.getBody().get("message"));
    }

    @Test
    void testUpdateOrderListing() {
        // Mocking the service response
        OrderListing OrderListing = new OrderListing();
        OrderListing.setOrderId("123");
        CompletableFuture<OrderListing> future = CompletableFuture.completedFuture(OrderListing);
        when(OrderListingService.update(any(OrderListing.class))).thenReturn(future);
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");

        // Perform PUT request
        OrderListing updated = new OrderListing();
        updated.setOrderId("321");
        updated.setListingId("456");
        ResponseEntity<Map<String, Object>> responseEntity =
                OrderListingController.updateOrderListing("mockedToken", updated).join();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
    @Test
    void updateOrderListingInternalError() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(OrderListingService.update(any(OrderListing.class))).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<Map<String, Object>> responseEntity =
                OrderListingController.updateOrderListing("mockedToken", new OrderListing()).join();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void updateOrderListingExpiredJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenThrow(ExpiredJwtException.class);

        ResponseEntity<Map<String, Object>> responseEntity =
                OrderListingController.updateOrderListing("mockedToken", new OrderListing()).join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("You are not authorized to make this request", responseEntity.getBody().get("message"));
    }

    @Test
    void updateOrderListingEmptyJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn(null);

        ResponseEntity<Map<String, Object>> responseEntity =
                OrderListingController.updateOrderListing("mockedToken", new OrderListing()).join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("You are not authorized to make this request", responseEntity.getBody().get("message"));
    }

}
