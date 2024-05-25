package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.enums.ReportTargetType;
import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ReportControllerTest {

    @InjectMocks
    private ReportController reportController;

    @Mock
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReport_Success() {
        Report report = new Report.Builder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportService.createReport(any(Report.class))).thenReturn(CompletableFuture.completedFuture(report));

        ResponseEntity<Report> response = reportController.createReport(report).join(); // Ensure CompletableFuture completes

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(report, response.getBody());
        verify(reportService, times(1)).createReport(any(Report.class));
    }

    @Test
    void testUpdateReport_Success() {
        Report report = new Report.Builder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportService.updateReport(anyString(), any(Report.class))).thenReturn(CompletableFuture.completedFuture(report));

        ResponseEntity<Report> response = reportController.updateReport(UUID.randomUUID().toString(), report).join(); // Ensure CompletableFuture completes

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
        verify(reportService, times(1)).updateReport(anyString(), any(Report.class));
    }

    @Test
    void testDeleteReport_Success() {
        when(reportService.deleteReport(anyString())).thenReturn(CompletableFuture.completedFuture(null));

        ResponseEntity<Void> response = reportController.deleteReport(UUID.randomUUID().toString()).join(); // Ensure CompletableFuture completes

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reportService, times(1)).deleteReport(anyString());
    }

    @Test
    void testFindReportById_Success() {
        Report report = new Report.Builder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportService.findReportById(anyString())).thenReturn(CompletableFuture.completedFuture(Optional.of(report)));

        ResponseEntity<Optional<Report>> response = reportController.findReportById(UUID.randomUUID().toString()).join(); // Ensure CompletableFuture completes

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody().get());
        verify(reportService, times(1)).findReportById(anyString());
    }

    @Test
    void testFindReportById_NotFound() {
        when(reportService.findReportById(anyString())).thenReturn(CompletableFuture.completedFuture(Optional.empty()));

        ResponseEntity<Optional<Report>> response = reportController.findReportById(UUID.randomUUID().toString()).join(); // Ensure CompletableFuture completes

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(reportService, times(1)).findReportById(anyString());
    }

    @Test
    void testFindAllReports_Success() {
        List<Report> reports = new ArrayList<>();
        reports.add(new Report.Builder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build());
        reports.add(new Report.Builder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build());

        when(reportService.findAll()).thenReturn(CompletableFuture.completedFuture(reports));

        ResponseEntity<List<Report>> response = reportController.findAll().join(); // Ensure CompletableFuture completes

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
        verify(reportService, times(1)).findAll();
    }

    @Test
    void testFindReportsByItemId_Success() {
        List<Report> reports = new ArrayList<>();
        reports.add(new Report.Builder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build());
        reports.add(new Report.Builder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build());

        when(reportService.findReportsByItemId(anyString())).thenReturn(CompletableFuture.completedFuture(reports));

        ResponseEntity<List<Report>> response = reportController.findReportsByItemId("13652556-012a-4c07-b546-54eb1396d79b").join(); // Ensure CompletableFuture completes

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
        verify(reportService, times(1)).findReportsByItemId(anyString());
    }

    @Test
    void testFindReportsByUserId_Success() {
        List<Report> reports = new ArrayList<>();
        reports.add(new Report.Builder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.USER)
                .build());
        reports.add(new Report.Builder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.USER)
                .build());

        when(reportService.findReportsByUserId(anyString())).thenReturn(CompletableFuture.completedFuture(reports));

        ResponseEntity<List<Report>> response = reportController.findReportsByUserId("13652556-012a-4c07-b546-54eb1396d79b").join(); // Ensure CompletableFuture completes

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
        verify(reportService, times(1)).findReportsByUserId(anyString());
    }

    @Test
    void testFindReportsByAuthorId_Success() {
        List<Report> reports = new ArrayList<>();
        reports.add(new Report.Builder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.USER)
                .build());
        reports.add(new Report.Builder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.USER)
                .build());

        when(reportService.findReportsByAuthorId(anyString())).thenReturn(CompletableFuture.completedFuture(reports));

        ResponseEntity<List<Report>> response = reportController.findReportsByAuthorId("a2c62328-4a37-4664-83c7-f32db8620155").join(); // Ensure CompletableFuture completes

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
        verify(reportService, times(1)).findReportsByAuthorId(anyString());
    }
}


