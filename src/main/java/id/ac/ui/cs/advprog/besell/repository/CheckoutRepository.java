package id.ac.ui.cs.advprog.besell.repository;

import id.ac.ui.cs.advprog.besell.model.Checkout;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Repository
public class CheckoutRepository {

    public CompletableFuture<Checkout> processCheckout(Checkout checkout) {
        return CompletableFuture.completedFuture(checkout);
    }
}
