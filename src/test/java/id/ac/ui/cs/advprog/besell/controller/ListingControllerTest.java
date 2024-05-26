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

import id.ac.ui.cs.advprog.besell.model.Listing;
import id.ac.ui.cs.advprog.besell.service.ListingService;
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

public class ListingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ListingService listingService;

    @InjectMocks
    private ListingController listingController;

    public ListingControllerTest() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(listingController).build();
    }

    @Test
    public void testFindById() throws Exception {
        // Mocking the service response
        Listing listing = new Listing();
        listing.setId("123");
        listing.setName("Test Listing");
        CompletableFuture<Optional<Listing>> future = CompletableFuture.completedFuture(Optional.of(listing));
        when(listingService.findById(anyString())).thenReturn(future);

        MvcResult result = mockMvc.perform(get("/listing/123"))
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
        CompletableFuture<Optional<Listing>> future = CompletableFuture.completedFuture(Optional.empty());
        when(listingService.findById(anyString())).thenReturn(future);

        MvcResult result = mockMvc.perform(get("/listing/123"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateListing() throws Exception {
        // Mocking the service response
        Listing listing = new Listing();
        listing.setId("123");
        listing.setName("Test Listing");
        CompletableFuture<Listing> future = CompletableFuture.completedFuture(listing);
        when(listingService.create(any(Listing.class))).thenReturn(future);

        // Perform POST request
        MvcResult result = mockMvc.perform(post("/listing")
                        .contentType("application/json")
                        .content("{\"id\":\"123\",\"name\":\"Test Listing\"}"))
                        .andExpect(status().isOk())
                        .andExpect(request().asyncStarted())
                        .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.listing.id").value("123"))
                .andExpect(jsonPath("$.listing.name").value("Test Listing"))
                .andExpect(jsonPath("$.message").value("Listing Created Successfully"));
    }

    @Test
    public void testDeleteListing() throws Exception {
        // Mocking the service response
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        when(listingService.delete("123")).thenReturn(future);

        // Perform DELETE request
        MvcResult result = mockMvc.perform(delete("/listing/123"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Listing Deleted Successfully"));
    }

    @Test
    public void testUpdateListing() throws Exception {
        // Mocking the service response
        Listing listing = new Listing();
        listing.setId("123");
        listing.setName("Updated Listing");
        CompletableFuture<Listing> future = CompletableFuture.completedFuture(listing);
        when(listingService.update(any(Listing.class))).thenReturn(future);

        // Perform PUT request
        MvcResult result = mockMvc.perform(put("/listing")
                        .contentType("application/json")
                        .content("{\"id\":\"123\",\"name\":\"Updated Listing\"}"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.listing.id").value("123"))
                .andExpect(jsonPath("$.listing.name").value("Updated Listing"))
                .andExpect(jsonPath("$.message").value("Listing ID 123 updated Successfully"));
    }

    @Test
    public void testFindAllListings() throws Exception {
        // Mocking the service response
        List<Listing> listings = Arrays.asList(new Listing(), new Listing());
        CompletableFuture<List<Listing>> future = CompletableFuture.completedFuture(listings);
        when(listingService.findAll()).thenReturn(future);

        // Perform GET request
        MvcResult result = mockMvc.perform(get("/listing"))
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
        Listing listing = new Listing();
        listing.setId("123");
        listing.setName("Test Listing");
        listing.setSellerId("TestId");
        CompletableFuture<List<Listing>> future = CompletableFuture.completedFuture(List.of(listing));
        when(listingService.findBySellerId(anyString())).thenReturn(future);

        MvcResult result = mockMvc.perform(get("/listing/seller/TestId"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("123"))
                .andExpect(jsonPath("$[0].sellerId").value("TestId"));
    }
}
