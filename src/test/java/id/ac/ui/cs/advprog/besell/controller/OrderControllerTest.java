package id.ac.ui.cs.advprog.besell.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.*;

import id.ac.ui.cs.advprog.besell.config.JwtAuthFilter;
import id.ac.ui.cs.advprog.besell.model.Order;
import id.ac.ui.cs.advprog.besell.service.OrderService;
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

class OrderControllerTest {

    private final MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @Mock
    private JwtAuthFilter jwtAuthFilter;

    @InjectMocks
    private OrderController orderController;

    public OrderControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testFindById() throws Exception {
        // Mocking the service response
        Order order = new Order();
        order.setId("123");
        order.setBuyerId("TestId");
        CompletableFuture<Optional<Order>> future = CompletableFuture.completedFuture(Optional.of(order));
        when(orderService.findById(anyString())).thenReturn(future);

        MvcResult result = mockMvc.perform(get("/order/123"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        // Mocking the service response
        CompletableFuture<Optional<Order>> future = CompletableFuture.completedFuture(Optional.empty());
        when(orderService.findById(anyString())).thenReturn(future);

        MvcResult result = mockMvc.perform(get("/order/123"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByIdInternalError() throws Exception {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(orderService.findById(any(String.class))).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<?> responseEntity =
                orderController.findById("fakeId").get();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }


    @Test
    void testFindAllOrders() throws Exception {
        // Mocking the service response
        List<Order> orders = Arrays.asList(new Order(), new Order());
        CompletableFuture<List<Order>> future = CompletableFuture.completedFuture(orders);
        when(orderService.findAll()).thenReturn(future);

        // Perform GET request
        MvcResult result = mockMvc.perform(get("/order"))
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
        when(orderService.findAll()).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<List<Order>> responseEntity =
                orderController.findAllOrders().get();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testFindBySellerId() throws Exception {
        // Mocking the service response
        Order order = new Order();
        order.setId("123");
        order.setSellerId("TestId");
        CompletableFuture<List<Order>> future = CompletableFuture.completedFuture(List.of(order));
        when(orderService.findBySellerId(anyString())).thenReturn(future);

        MvcResult result = mockMvc.perform(get("/order/seller/TestId"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("123"))
                .andExpect(jsonPath("$[0].sellerId").value("TestId"));
    }

    @Test
    void findBySellerIdInternalError() throws Exception {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(orderService.findBySellerId(any(String.class))).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<?> responseEntity =
                orderController.findBySellerId("fakeId").get();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testFindBySellerId_NotFound() throws Exception {
        // Mocking the service response
        CompletableFuture<List<Order>> future = CompletableFuture.completedFuture(Collections.emptyList());
        when(orderService.findBySellerId(anyString())).thenReturn(future);

        MvcResult result = mockMvc.perform(get("/order/seller/123"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateOrder() throws Exception {
        // Mocking the service response
        Order order = new Order();
        order.setId("123");
        order.setBuyerId("TestId");
        CompletableFuture<Order> future = CompletableFuture.completedFuture(order);
        when(orderService.create(any(Order.class))).thenReturn(future);
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");

        // Perform POST request
        MvcResult result = mockMvc.perform(post("/order")
                        .contentType("application/json")
                        .header("Authorization", "mockToken")
                        .content("{\"id\":\"123\",\"name\":\"Test Order\"}"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.order.id").value("123"))
                .andExpect(jsonPath("$.order.buyerId").value("TestId"))
                .andExpect(jsonPath("$.message").value("Order Created Successfully"));
    }

    @Test
    void createOrderInternalError() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(orderService.create(any(Order.class))).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<Map<String, Object>> responseEntity =
                orderController.createOrder("mockedToken", new Order()).join();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void createOrderExpiredJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenThrow(ExpiredJwtException.class);

        ResponseEntity<Map<String, Object>> responseEntity =
                orderController.createOrder("mockedToken", new Order()).join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    void createOrderEmptyJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn(null);

        ResponseEntity<Map<String, Object>> responseEntity =
                orderController.createOrder("mockedToken", new Order()).join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("You are not authorized to make this request", responseEntity.getBody().get("message"));
    }

    @Test
    void testDeleteOrder() throws Exception {
        // Mocking the service response
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(orderService.delete("123")).thenReturn(future);

        // Perform DELETE request
        MvcResult result = mockMvc.perform(delete("/order/123").header("Authorization", "mockToken"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Order Deleted Successfully"));
    }

    @Test
    void deleteOrderInternalError() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(orderService.delete(any(String.class))).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<Map<String, Object>> responseEntity =
                orderController.deleteOrder("mockedToken", "mockId").join();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void deleteOrderExpiredJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenThrow(ExpiredJwtException.class);

        ResponseEntity<Map<String, Object>> responseEntity =
                orderController.deleteOrder("mockedToken", "mockId").join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    void deleteOrderEmptyJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn(null);

        ResponseEntity<Map<String, Object>> responseEntity =
                orderController.deleteOrder("mockedToken", "mockId").join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("You are not authorized to make this request", responseEntity.getBody().get("message"));
    }

    @Test
    void testUpdateOrder() throws Exception {
        // Mocking the service response
        Order order = new Order();
        order.setId("123");
        order.setBuyerId("TestId");
        CompletableFuture<Order> future = CompletableFuture.completedFuture(order);
        when(orderService.update(any(Order.class))).thenReturn(future);
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");

        // Perform PUT request
        MvcResult result = mockMvc.perform(put("/order")
                        .contentType("application/json")
                        .header("Authorization", "mockToken")
                        .content("{\"id\":\"123\",\"name\":\"Updated Order\"}"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.order.id").value("123"))
                .andExpect(jsonPath("$.order.buyerId").value("TestId"))
                .andExpect(jsonPath("$.message").value("Order ID 123 updated Successfully"));
    }
    @Test void updateOrderInternalError() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(orderService.update(any(Order.class))).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<Map<String, Object>> responseEntity =
                orderController.updateOrder("mockedToken", new Order()).join();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void updateOrderExpiredJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenThrow(ExpiredJwtException.class);

        ResponseEntity<Map<String, Object>> responseEntity =
                orderController.updateOrder("mockedToken", new Order()).join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("You are not authorized to make this request", responseEntity.getBody().get("message"));
    }

    @Test
    void updateOrderEmptyJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn(null);

        ResponseEntity<Map<String, Object>> responseEntity =
                orderController.updateOrder("mockedToken", new Order()).join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("You are not authorized to make this request", responseEntity.getBody().get("message"));
    }

}
