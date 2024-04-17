package id.ac.ui.cs.advprog.besell.repository;

import id.ac.ui.cs.advprog.besell.model.Listing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ListingRepoTest {
    @InjectMocks
    ListingRepository listingRepository;
    @BeforeEach
    void SetUp(){

    }

    @Test
    void testCreateAndFind() {
    }

    @Test
    void testFindAllIfEmpty() {
    }

    @Test
    void testFindAllIfMoreThanOneListing() {
    }

    @Test
    void testEditListing() {
    }

    @Test
    void testDeleteListing() {
    }
}
