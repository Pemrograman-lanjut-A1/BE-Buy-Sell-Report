package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.enums.ReportTargetType;
import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.model.ReportBuilder;
import id.ac.ui.cs.advprog.besell.service.JwtService;
import id.ac.ui.cs.advprog.besell.service.ReportService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ReportControllerTest {

    @InjectMocks
    private ReportController reportController;

    private MockMvc mockMvc;

    @Mock
    private ReportService reportService;

    @Mock
    private JwtService jwtService;

    private String validToken;
    private Claims validClaims;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();

        validToken = "Bearer validToken";
        validClaims = new DefaultClaims();
        validClaims.put("Role", "USER");
        validClaims.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60));
    }

    @Test
    void testCreateReport_Success() {
        Report report = new ReportBuilder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportService.createReport(any(Report.class))).thenReturn(CompletableFuture.completedFuture(report));
        when(jwtService.resolveClaims(validToken)).thenReturn(validClaims);
        when(jwtService.validateClaims(validClaims)).thenReturn(true);

        ResponseEntity<Report> response = reportController.createReport(validToken, report).join();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(report, response.getBody());
        verify(reportService, times(1)).createReport(any(Report.class));
    }

    @Test
    void testUpdateReport_Success() {
        Report report = new ReportBuilder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportService.updateReport(anyString(), any(Report.class))).thenReturn(CompletableFuture.completedFuture(report));
        when(jwtService.resolveClaims(validToken)).thenReturn(validClaims);
        when(jwtService.validateClaims(validClaims)).thenReturn(true);

        ResponseEntity<Report> response = reportController.updateReport(validToken, UUID.randomUUID().toString(), report).join();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
        verify(reportService, times(1)).updateReport(anyString(), any(Report.class));
    }

    @Test
    void testDeleteReport_Success() {
        when(reportService.deleteReport(anyString())).thenReturn(CompletableFuture.completedFuture(null));
        when(jwtService.resolveClaims(validToken)).thenReturn(validClaims);
        when(jwtService.validateClaims(validClaims)).thenReturn(true);

        ResponseEntity<Void> response = reportController.deleteReport(validToken, UUID.randomUUID().toString()).join();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reportService, times(1)).deleteReport(anyString());
    }

    @Test
    void testFindReportById_Success() {
        Report report = new ReportBuilder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportService.findReportById(anyString())).thenReturn(CompletableFuture.completedFuture(Optional.of(report)));

        ResponseEntity<Optional<Report>> response = reportController.findReportById(UUID.randomUUID().toString()).join();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody().get());
        verify(reportService, times(1)).findReportById(anyString());
    }

    @Test
    void testFindReportById_NotFound() {
        when(reportService.findReportById(anyString())).thenReturn(CompletableFuture.completedFuture(Optional.empty()));

        ResponseEntity<Optional<Report>> response = reportController.findReportById(UUID.randomUUID().toString()).join();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(reportService, times(1)).findReportById(anyString());
    }

    @Test
    void testFindAllReports_Success() {
        List<Report> reports = new ArrayList<>();
        reports.add(new ReportBuilder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build());
        reports.add(new ReportBuilder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build());

        when(reportService.findAll()).thenReturn(CompletableFuture.completedFuture(reports));

        ResponseEntity<List<Report>> response = reportController.findAll().join();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
        verify(reportService, times(1)).findAll();
    }

    @Test
    void testFindReportsByItemId_Success() {
        List<Report> reports = new ArrayList<>();
        reports.add(new ReportBuilder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build());
        reports.add(new ReportBuilder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build());

        when(reportService.findReportsByItemId(anyString())).thenReturn(CompletableFuture.completedFuture(reports));

        ResponseEntity<List<Report>> response = reportController.findReportsByItemId("13652556-012a-4c07-b546-54eb1396d79b").join();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
        verify(reportService, times(1)).findReportsByItemId(anyString());
    }

    @Test
    void testFindReportsByUserId_Success() {
        List<Report> reports = new ArrayList<>();
        reports.add(new ReportBuilder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.USER)
                .build());
        reports.add(new ReportBuilder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.USER)
                .build());

        when(reportService.findReportsByUserId(anyString())).thenReturn(CompletableFuture.completedFuture(reports));

        ResponseEntity<List<Report>> response = reportController.findReportsByUserId("13652556-012a-4c07-b546-54eb1396d79b").join();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
        verify(reportService, times(1)).findReportsByUserId(anyString());
    }

    @Test
    void testFindReportsByAuthorId_Success() {
        List<Report> reports = new ArrayList<>();
        reports.add(new ReportBuilder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.USER)
                .build());
        reports.add(new ReportBuilder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.USER)
                .build());

        when(reportService.findReportsByAuthorId(anyString())).thenReturn(CompletableFuture.completedFuture(reports));

        ResponseEntity<List<Report>> response = reportController.findReportsByAuthorId("a2c62328-4a37-4664-83c7-f32db8620155").join();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
        verify(reportService, times(1)).findReportsByAuthorId(anyString());
    }

    @Test
    void testCreateReport_Unauthorized() throws Exception {
        // Mock JwtService to return null for invalid token
        when(jwtService.resolveClaims(anyString())).thenReturn(null);

        Report report = new ReportBuilder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build();

        mockMvc.perform(post("/report/create")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer invalid_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"authorId\": \"a2c62328-4a37-4664-83c7-f32db8620155\", \"description\": \"description\", \"targetId\": \"13652556-012a-4c07-b546-54eb1396d79b\", \"reportDate\": \"2024-05-26T12:34:56\", \"targetType\": \"ITEM\" }"))
                .andExpect(status().isUnauthorized());

        verify(reportService, never()).createReport(any(Report.class));
    }

    @Test
    void testUpdateReport_Unauthorized() throws Exception {
        // Mock JwtService to return null for invalid token
        when(jwtService.resolveClaims(anyString())).thenReturn(null);

        Report report = new ReportBuilder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("updated description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build();

        mockMvc.perform(put("/report/update/{id}", UUID.randomUUID().toString())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer invalid_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"authorId\": \"a2c62328-4a37-4664-83c7-f32db8620155\", \"description\": \"updated description\", \"targetId\": \"13652556-012a-4c07-b546-54eb1396d79b\", \"reportDate\": \"2024-05-26T12:34:56\", \"targetType\": \"ITEM\" }"))
                .andExpect(status().isUnauthorized());

        verify(reportService, never()).updateReport(anyString(), any(Report.class));
    }

    @Test
    void testDeleteReport_Unauthorized() throws Exception {
        // Mock JwtService to return null for invalid token
        when(jwtService.resolveClaims(anyString())).thenReturn(null);

        mockMvc.perform(delete("/report/{id}", UUID.randomUUID().toString())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer invalid_token"))
                .andExpect(status().isUnauthorized());

        verify(reportService, never()).deleteReport(anyString());
    }
}


