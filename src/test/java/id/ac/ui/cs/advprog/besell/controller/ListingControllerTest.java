package id.ac.ui.cs.advprog.besell.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import id.ac.ui.cs.advprog.besell.config.JwtAuthFilter;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.*;

import id.ac.ui.cs.advprog.besell.model.Listing;
import id.ac.ui.cs.advprog.besell.service.ListingService;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ListingControllerTest {

    private final MockMvc mockMvc;

    @Mock
    private ListingService listingService;

    @Mock
    private JwtAuthFilter jwtAuthFilter;


    @InjectMocks
    private ListingController listingController;

    public ListingControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(listingController).build();
    }

    @Test
    void testFindById() throws Exception {
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
    void testFindById_NotFound() throws Exception {
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
    void findByIdInternalError() throws Exception {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(listingService.findById(any(String.class))).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<?> responseEntity =
                listingController.findById("fakeId").get();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testFindAllListings() throws Exception {
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
    void findAllInternalError() throws Exception {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(listingService.findAll()).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<List<Listing>> responseEntity =
                listingController.findAllListings().get();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testFindBySellerId() throws Exception {
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

    @Test
    void findBySellerIdInternalError() throws Exception {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(listingService.findBySellerId(any(String.class))).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<?> responseEntity =
                listingController.findBySellerId("fakeId").get();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testFindBySellerId_NotFound() throws Exception {
        // Mocking the service response
        CompletableFuture<List<Listing>> future = CompletableFuture.completedFuture(Collections.emptyList());
        when(listingService.findBySellerId(anyString())).thenReturn(future);

        MvcResult result = mockMvc.perform(get("/listing/seller/123"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateListing() throws Exception {
        // Mocking the service response
        Listing listing = new Listing();
        listing.setId("123");
        listing.setName("Test Listing");
        CompletableFuture<Listing> future = CompletableFuture.completedFuture(listing);
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(listingService.create(any(Listing.class))).thenReturn(future);

        // Perform POST request
        MvcResult result = mockMvc.perform(post("/listing")
                        .contentType("application/json")
                        .header("Authorization", "mockToken")
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
    void createListingInternalError() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(listingService.create(any(Listing.class))).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<Map<String, Object>> responseEntity =
                listingController.createListing("mockedToken", new Listing()).join();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void createListingExpiredJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenThrow(ExpiredJwtException.class);

        ResponseEntity<Map<String, Object>> responseEntity =
                listingController.createListing("mockedToken", new Listing()).join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    void createListingEmptyJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn(null);

        ResponseEntity<Map<String, Object>> responseEntity =
                listingController.createListing("mockedToken", new Listing()).join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("You are not authorized to make this request", responseEntity.getBody().get("message"));
    }

    @Test
    void testDeleteListing() throws Exception {
        // Mocking the service response
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(listingService.delete("123")).thenReturn(future);

        // Perform DELETE request
        MvcResult result = mockMvc.perform(
                delete("/listing/123")
                        .header("Authorization", "mockToken")
                )
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Listing Deleted Successfully"));
    }
    @Test
    void deleteListingInternalError() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(listingService.delete(any(String.class))).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<Map<String, Object>> responseEntity =
                listingController.deleteListing("mockedToken", "mockId").join();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void deleteListingExpiredJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenThrow(ExpiredJwtException.class);

        ResponseEntity<Map<String, Object>> responseEntity =
                listingController.deleteListing("mockedToken", "mockId").join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    void deleteListingEmptyJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn(null);

        ResponseEntity<Map<String, Object>> responseEntity =
                listingController.deleteListing("mockedToken", "mockId").join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("You are not authorized to make this request", responseEntity.getBody().get("message"));
    }

    @Test
    void testUpdateListing() throws Exception {
        // Mocking the service response
        Listing listing = new Listing();
        listing.setId("123");
        listing.setName("Updated Listing");
        CompletableFuture<Listing> future = CompletableFuture.completedFuture(listing);
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(listingService.update(any(Listing.class))).thenReturn(future);

        // Perform PUT request
        MvcResult result = mockMvc.perform(put("/listing")
                        .contentType("application/json")
                        .header("Authorization", "mockToken")
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
    void updateListingInternalError() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn("ADMIN");
        when(listingService.update(any(Listing.class))).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Simulated exception");
        }));

        // Act
        ResponseEntity<Map<String, Object>> responseEntity =
                listingController.updateListing("mockedToken", new Listing()).join();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void updateListingExpiredJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenThrow(ExpiredJwtException.class);

        ResponseEntity<Map<String, Object>> responseEntity =
                listingController.updateListing("mockedToken", new Listing()).join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("You are not authorized to make this request", responseEntity.getBody().get("message"));
    }

    @Test
    void updateListingEmptyJwtToken() {
        when(jwtAuthFilter.filterToken(anyString())).thenReturn(null);

        ResponseEntity<Map<String, Object>> responseEntity =
                listingController.updateListing("mockedToken", new Listing()).join();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("You are not authorized to make this request", responseEntity.getBody().get("message"));
    }

}
