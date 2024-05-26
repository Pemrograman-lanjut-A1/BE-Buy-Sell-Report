package id.ac.ui.cs.advprog.besell.service;
import id.ac.ui.cs.advprog.besell.model.Listing;
import id.ac.ui.cs.advprog.besell.model.OrderListing;
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
    void testCreateAndFind(){
        OrderListing orderListing = new OrderListing();
        orderListing.setOrderId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderListing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderListing.setQuantity(100);

        Mockito.when(orderListingRepository.save(orderListing)).thenReturn(orderListing);
        orderListingService.create(orderListing);

        Mockito.when(orderListingRepository.findAll()).thenReturn(List.of(orderListing));
        List<OrderListing> orderListingList = orderListingService.findAll();

        assertFalse(orderListingList.isEmpty());
        OrderListing saved= orderListingList.getFirst();
        assertEquals(orderListing.getOrderId(), saved.getOrderId());
        assertEquals(orderListing.getListingId(), saved.getListingId());
        assertEquals(orderListing.getQuantity(), saved.getQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        List<OrderListing> orderListingList = new ArrayList<>();
        Mockito.when(orderListingRepository.findAll()).thenReturn(orderListingList);

        List<OrderListing> products = orderListingService.findAll();

        assertTrue(products.isEmpty());
    }

    @Test
    void testEditListing() {
        OrderListing orderListing = new OrderListing();
        orderListing.setOrderId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderListing.setListingId("Sampo Cap Bambang");
        orderListing.setQuantity(100);

        Mockito.when(orderListingRepository.save(orderListing)).thenReturn(orderListing);
        orderListingService.create(orderListing);

        OrderListing editedOrderListing = new OrderListing();
        editedOrderListing.setOrderId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        editedOrderListing.setListingId("Sampo Cap Bango");
        editedOrderListing.setQuantity(0);
        orderListingService.update(editedOrderListing);

        Mockito.when(orderListingRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(Optional.of(editedOrderListing));
        Optional<OrderListing> resultOrderListing = orderListingService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertEquals(editedOrderListing, resultOrderListing.get());
        Mockito.verify(orderListingRepository).save(editedOrderListing);
    }

    @Test
    void testDeleteListing() {
        OrderListing orderListing1 = new OrderListing();
        orderListing1.setOrderId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        orderListing1.setListingId("Sampo Cap Bambang");
        orderListing1.setQuantity(100);

        Mockito.when(orderListingRepository.save(orderListing1)).thenReturn(orderListing1);
        orderListingService.create(orderListing1);

        orderListingService.delete(orderListing1.getOrderId());

        Mockito.verify(orderListingRepository).deleteById(orderListing1.getOrderId());
    }
}
