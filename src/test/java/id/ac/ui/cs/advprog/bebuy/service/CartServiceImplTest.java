package id.ac.ui.cs.advprog.bebuy.service;

import id.ac.ui.cs.advprog.besell.service.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CartServiceImplTest {

    @Mock
    //private CartItem mockCartItem;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddItemToCart() {
    }

    @Test
    void testGetCartItems() {
    }

    @Test
    void testUpdateItemQuantity() {
    }

    @Test
    void testRemoveItemFromCart() {
    }
}