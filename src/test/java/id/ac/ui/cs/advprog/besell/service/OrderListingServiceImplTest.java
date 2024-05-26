package id.ac.ui.cs.advprog.besell.service;
import id.ac.ui.cs.advprog.besell.model.Listing;
import id.ac.ui.cs.advprog.besell.model.Order;
import id.ac.ui.cs.advprog.besell.model.OrderListing;
import id.ac.ui.cs.advprog.besell.model.builder.OrderBuilder;
import id.ac.ui.cs.advprog.besell.repository.ListingRepository;
import id.ac.ui.cs.advprog.besell.repository.OrderListingRepository;
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
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OrderListingServiceImplTest {
    @InjectMocks
    OrderListingServiceImpl orderListingService;

    @Mock
    OrderListingRepository orderListingRepository;

    @BeforeEach
    void SetUp(){

    }

    @Test
    void testCreateAndFind() throws ExecutionException, InterruptedException{
        OrderListing orderListing = new OrderListing();
        orderListing.setOrderId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderListing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderListing.setQuantity(100);

        Mockito.when(orderListingRepository.save(orderListing)).thenReturn(orderListing);
        orderListingService.create(orderListing);

        Mockito.when(orderListingRepository.findAll()).thenReturn(List.of(orderListing));
        CompletableFuture<List<OrderListing>> orderListingListFuture = orderListingService.findAll();
        List<OrderListing> orderListingList = orderListingListFuture.get();

        assertFalse(orderListingList.isEmpty());
        OrderListing saved= orderListingList.getFirst();
        assertEquals(orderListing.getOrderId(), saved.getOrderId());
        assertEquals(orderListing.getListingId(), saved.getListingId());
        assertEquals(orderListing.getQuantity(), saved.getQuantity());
    }

    @Test
    void testFindAllIfEmpty() throws ExecutionException, InterruptedException{
        List<OrderListing> orderListingList = new ArrayList<>();
        Mockito.when(orderListingRepository.findAll()).thenReturn(orderListingList);

        CompletableFuture<List<OrderListing>> productsFuture = orderListingService.findAll();
        List<OrderListing> products = productsFuture.get();

        assertTrue(products.isEmpty());
    }

    @Test
    void testFindByOrderId() throws ExecutionException, InterruptedException{
        OrderListing orderListing = new OrderListing();
        orderListing.setOrderId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderListing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderListing.setQuantity(100);

        Mockito.when(orderListingRepository.save(orderListing)).thenReturn(orderListing);
        orderListingService.create(orderListing);

        OrderListing orderListing2 = new OrderListing();
        orderListing.setOrderId("id1");
        orderListing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderListing.setQuantity(100);

        Mockito.when(orderListingRepository.save(orderListing2)).thenReturn(orderListing2);
        orderListingService.create(orderListing2);

        Mockito.when(orderListingRepository.findByOrderId("id1")).thenReturn(List.of(orderListing2));
        CompletableFuture<List<OrderListing>> orderListingListFuture = orderListingService.findByOrderId("id1");
        List<OrderListing> orderListingList = orderListingListFuture.get();

        assertFalse(orderListingList.isEmpty());
        OrderListing saved = orderListingList.getFirst();
        assertEquals(orderListing2.getOrderId(), saved.getOrderId());
        assertEquals(orderListing2.getListingId(), saved.getListingId());
        assertEquals(orderListing2.getQuantity(), saved.getQuantity());
    }

    @Test
    void testDeleteListing() throws ExecutionException, InterruptedException{
        OrderListing orderListing1 = new OrderListing();
        orderListing1.setOrderId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderListing1.setListingId("Sampo Cap Bambang");
        orderListing1.setQuantity(100);

        Mockito.when(orderListingRepository.save(orderListing1)).thenReturn(orderListing1);
        orderListingService.create(orderListing1);

        orderListingService.delete(orderListing1.getOrderId());

        Mockito.verify(orderListingRepository).deleteById(orderListing1.getOrderId());
    }

    @Test
    void testEditOrderListing() throws ExecutionException, InterruptedException{
        OrderListing orderListing = new OrderListing();
        orderListing.setOrderId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderListing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderListing.setQuantity(100);

        Mockito.when(orderListingRepository.save(orderListing)).thenReturn(orderListing);
        orderListingService.create(orderListing);

        OrderListing orderListing2 = new OrderListing();
        orderListing.setOrderId("id1");
        orderListing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderListing.setQuantity(100);

        orderListingService.update(orderListing2);

        Mockito.when(orderListingRepository.save(orderListing2)).thenReturn(orderListing2);
        CompletableFuture<OrderListing> resultOrderFuture = orderListingService.update(orderListing2);
        OrderListing result = resultOrderFuture.get();

        assertEquals(orderListing2, result);
        Mockito.verify(orderListingRepository, times(2)).save(orderListing2);
    }
}
