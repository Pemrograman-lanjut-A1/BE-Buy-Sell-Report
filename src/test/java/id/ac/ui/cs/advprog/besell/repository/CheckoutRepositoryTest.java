package id.ac.ui.cs.advprog.besell.repository;

import id.ac.ui.cs.advprog.besell.model.Checkout;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CheckoutRepositoryTest {

    @Mock
    private CheckoutRepository checkoutRepository;

    @Test
    public void testProcessCheckout() {
        Checkout checkout = new Checkout();
        when(checkoutRepository.processCheckout(checkout))
                .thenReturn(CompletableFuture.completedFuture(checkout));
        CompletableFuture<Checkout> result = checkoutRepository.processCheckout(checkout);
        assertEquals(checkout, result.join());
    }
}
