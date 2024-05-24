package id.ac.ui.cs.advprog.besell.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.*;

import id.ac.ui.cs.advprog.besell.model.Order;
import id.ac.ui.cs.advprog.besell.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    public OrderControllerTest() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testFindById() throws Exception {
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
    public void testFindById_NotFound() throws Exception {
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
    public void testCreateOrder() throws Exception {
        // Mocking the service response
        Order order = new Order();
        order.setId("123");
        order.setBuyerId("TestId");
        CompletableFuture<Order> future = CompletableFuture.completedFuture(order);
        when(orderService.create(any(Order.class))).thenReturn(future);

        // Perform POST request
        MvcResult result = mockMvc.perform(post("/order")
                        .contentType("application/json")
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
    public void testDeleteOrder() throws Exception {
        // Mocking the service response
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        when(orderService.delete("123")).thenReturn(future);

        // Perform DELETE request
        MvcResult result = mockMvc.perform(delete("/order/123"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Order Deleted Successfully"));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        // Mocking the service response
        Order order = new Order();
        order.setId("123");
        order.setBuyerId("TestId");
        CompletableFuture<Order> future = CompletableFuture.completedFuture(order);
        when(orderService.update(any(Order.class))).thenReturn(future);

        // Perform PUT request
        MvcResult result = mockMvc.perform(put("/order")
                        .contentType("application/json")
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

    @Test
    public void testFindAllOrders() throws Exception {
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
    public void testFindBySellerId() throws Exception {
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
}
