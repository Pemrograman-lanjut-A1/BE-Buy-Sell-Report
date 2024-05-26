package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Order;
import id.ac.ui.cs.advprog.besell.model.builder.OrderBuilder;
import id.ac.ui.cs.advprog.besell.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    @BeforeEach
    void SetUp(){

    }

    @Test
    void testCreateAndFind() throws ExecutionException, InterruptedException {
        OrderBuilder builder = new OrderBuilder("FakeBuyerId", "FakeSellerId");
        Order order = builder.build();

        Mockito.when(orderRepository.save(order)).thenReturn(order);
        orderService.create(order);

        Mockito.when(orderRepository.findAll()).thenReturn(List.of(order));
        CompletableFuture<List<Order>> orderListFuture = orderService.findAll();
        List<Order> orderList = orderListFuture.get();

        assertFalse(orderList.isEmpty());
        Order savedOrder = orderList.getFirst();
        assertEquals(order.getId(), savedOrder.getId());
        assertEquals(order.getStatus(), savedOrder.getStatus());
        assertEquals(order.getBuyerId(), savedOrder.getBuyerId());
    }

    @Test
    void testFindAllIfEmpty() throws ExecutionException, InterruptedException{
        List<Order> orderList = new ArrayList<>();
        Mockito.when(orderRepository.findAll()).thenReturn(orderList);

        CompletableFuture<List<Order>> ordersFuture = orderService.findAll();
        List<Order> orders = ordersFuture.get();


        assertTrue(orders.isEmpty());
    }

    @Test
    void testFindAllIfMoreThanOneOrder() throws ExecutionException, InterruptedException{
        OrderBuilder builder = new OrderBuilder("FakeItemId", "FakeSellerId");
        Order order1 = builder.setBuyerId("FakeBuyerId")
                .build();

        Mockito.when(orderRepository.save(order1)).thenReturn(order1);
        orderService.create(order1);

        Order order2 = builder.setBuyerId("FakeBuyerId2")
                .build();
        Mockito.when(orderRepository.save(order2)).thenReturn(order2);
        orderService.create(order2);

        Mockito.when(orderRepository.findAll()).thenReturn(List.of(order1, order2));
        CompletableFuture<List<Order>> orderListFuture = orderService.findAll();
        List<Order> orderList = orderListFuture.get();

        assertFalse(orderList.isEmpty());
        assertEquals(order1.getId(), orderList.getFirst().getId());
        assertEquals(order2.getId(), orderList.get(1).getId());
        assertFalse(orderList.isEmpty());
    }

    @Test
    void testFindBySellerId() throws ExecutionException, InterruptedException{
        OrderBuilder builder = new OrderBuilder("FakeItemId", "FakeSellerId");
        Order order1 = builder.setBuyerId("FakeBuyerId").setSellerId("seller1")
                .build();

        Mockito.when(orderRepository.save(order1)).thenReturn(order1);
        orderService.create(order1);

        Order order2 = builder.setBuyerId("FakeBuyerId2").setSellerId("seller2")
                .build();
        Mockito.when(orderRepository.save(order2)).thenReturn(order2);
        orderService.create(order2);

        Mockito.when(orderRepository.findBySellerId("seller1")).thenReturn(List.of(order1));
        CompletableFuture<List<Order>> orderListFuture = orderService.findBySellerId("seller1");
        List<Order> orderList = orderListFuture.get();

        assertFalse(orderList.isEmpty());
        assertEquals(order1.getId(), orderList.getFirst().getId());
    }

    @Test
    void testEditOrder() throws ExecutionException, InterruptedException{
        OrderBuilder builder = new OrderBuilder("FakeItemId", "FakeSellerId");
        Order order = builder.setBuyerId("FakeBuyerId")
                .build();
        order.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        orderService.create(order);

        builder = new OrderBuilder("FakeItemId2", "FakeSellerId");
        Order editedOrder = builder.setBuyerId("FakeBuyerId")
                .build();
        editedOrder.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderService.update(editedOrder);

        Mockito.when(orderRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(Optional.of(editedOrder));
        CompletableFuture<Optional<Order>> resultOrderFuture = orderService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        Optional<Order> resultOrder = resultOrderFuture.get();

        assertEquals(editedOrder, resultOrder.get());
        Mockito.verify(orderRepository).save(editedOrder);
    }

    @Test
    void testDeleteOrder() {
        OrderBuilder builder = new OrderBuilder("FakeItemId", "FakeSellerId");
        Order order1 = builder.setBuyerId("FakeBuyerId")
                .build();

        Mockito.when(orderRepository.save(order1)).thenReturn(order1);
        orderService.create(order1);

        orderService.delete(order1.getId());

        Mockito.verify(orderRepository).deleteById(order1.getId());
    }
}
