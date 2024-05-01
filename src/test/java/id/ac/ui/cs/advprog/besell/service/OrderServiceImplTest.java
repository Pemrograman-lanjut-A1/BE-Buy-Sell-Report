package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Order;
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
    void testCreateAndFind() {
        Order.OrderBuilder builder = new Order.OrderBuilder("FakeItemId");
        Order order = builder.setBuyerId("FakeBuyerId")
                .build();

        Mockito.when(orderRepository.save(order)).thenReturn(order);
        orderService.create(order);

        Mockito.when(orderRepository.findAll()).thenReturn(List.of(order));
        List<Order> orderList = orderService.findAll();

        assertFalse(orderList.isEmpty());
        Order savedOrder = orderList.getFirst();
        assertEquals(order.getId(), savedOrder.getId());
        assertEquals(order.getStatus(), savedOrder.getStatus());
        assertEquals(order.getBuyerId(), savedOrder.getBuyerId());
    }

    @Test
    void testFindAllIfEmpty() {
        List<Order> orderList = new ArrayList<>();
        Mockito.when(orderRepository.findAll()).thenReturn(orderList);

        List<Order> orders = orderService.findAll();

        assertTrue(orders.isEmpty());
    }

    @Test
    void testFindAllIfMoreThanOneOrder() {
        Order.OrderBuilder builder = new Order.OrderBuilder("FakeItemId");
        Order order1 = builder.setBuyerId("FakeBuyerId")
                .build();

        Mockito.when(orderRepository.save(order1)).thenReturn(order1);
        orderService.create(order1);

        Order order2 = builder.setBuyerId("FakeBuyerId2")
                .build();
        Mockito.when(orderRepository.save(order2)).thenReturn(order2);
        orderService.create(order2);

        Mockito.when(orderRepository.findAll()).thenReturn(List.of(order1, order2));
        List<Order> orderList = orderService.findAll();

        assertFalse(orderList.isEmpty());
        assertEquals(order1.getId(), orderList.getFirst().getId());
        assertEquals(order2.getId(), orderList.get(1).getId());
        assertFalse(orderList.isEmpty());
    }

    @Test
    void testEditOrder() {
        Order.OrderBuilder builder = new Order.OrderBuilder("FakeItemId");
        Order order = builder.setBuyerId("FakeBuyerId")
                .build();
        order.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        orderService.create(order);

        builder = new Order.OrderBuilder("FakeItemId2");
        Order editedOrder = builder.setBuyerId("FakeBuyerId")
                .build();
        editedOrder.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderService.update(editedOrder);

        Mockito.when(orderRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(Optional.of(editedOrder));
        Optional<Order> resultOrder = orderService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertEquals(editedOrder, resultOrder.get());
        Mockito.verify(orderRepository).save(editedOrder);
    }

    @Test
    void testDeleteOrder() {
        Order.OrderBuilder builder = new Order.OrderBuilder("FakeItemId");
        Order order1 = builder.setBuyerId("FakeBuyerId")
                .build();

        Mockito.when(orderRepository.save(order1)).thenReturn(order1);
        orderService.create(order1);

        orderService.delete(order1.getId());

        Mockito.verify(orderRepository).deleteById(order1.getId());
    }
}
