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
        Report report = new Report();
        report.setAuthorId("authorId");
        report.setDescription("description");
        report.setTargetId("targetId");
        report.setReportDate(LocalDateTime.now());
        report.setTargetType(ReportTargetType.ITEM);

        when(reportService.createReport(any(Report.class))).thenReturn(report);

        ResponseEntity<Report> response = reportController.createReport(report);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(report, response.getBody());
        verify(reportService, times(1)).createReport(any(Report.class));
    }

    @Test
    void testUpdateReport_Success() {
        Report report = new Report();
        report.setAuthorId("authorId");
        report.setDescription("description");
        report.setTargetId("targetId");
        report.setReportDate(LocalDateTime.now());

        when(reportService.updateReport(anyString(), any(Report.class))).thenReturn(report);

        ResponseEntity<Report> response = reportController.updateReport(UUID.randomUUID().toString(), report);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
        verify(reportService, times(1)).updateReport(anyString(), any(Report.class));
    }

    @Test
    void testDeleteReport_Success() {
        doNothing().when(reportService).deleteReport(anyString());

        ResponseEntity<Void> response = reportController.deleteReport(UUID.randomUUID().toString());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reportService, times(1)).deleteReport(anyString());
    }

    @Test
    void testFindReportById_Success() {
        Report report = new Report();
        report.setAuthorId("authorId");
        report.setDescription("description");
        report.setTargetId("targetId");
        report.setReportDate(LocalDateTime.now());

        when(reportService.findReportById(anyString())).thenReturn(Optional.of(report));

        ResponseEntity<Optional<Report>> response = reportController.findReportById(UUID.randomUUID().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody().get());
        verify(reportService, times(1)).findReportById(anyString());
    }

    @Test
    void testFindReportById_NotFound() {
        when(reportService.findReportById(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Optional<Report>> response = reportController.findReportById(UUID.randomUUID().toString());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(reportService, times(1)).findReportById(anyString());
    }

    @Test
    void testFindAllReports_Success() {
        List<Report> reports = new ArrayList<>();
        reports.add(new Report());
        reports.add(new Report());

        when(reportService.findAll()).thenReturn(reports);

        ResponseEntity<List<Report>> response = reportController.findAllReports();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
        verify(reportService, times(1)).findAll();
    }

    @Test
    void testFindReportsByItemId_Success() {
        List<Report> reports = new ArrayList<>();
        reports.add(new Report());
        reports.add(new Report());

        when(reportService.findReportsByItemId(anyString())).thenReturn(reports);

        ResponseEntity<List<Report>> response = reportController.findReportsByItemId("itemId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
        verify(reportService, times(1)).findReportsByItemId(anyString());
    }

    @Test
    void testFindReportsByUserId_Success() {
        List<Report> reports = new ArrayList<>();
        reports.add(new Report());
        reports.add(new Report());

        when(reportService.findReportsByUserId(anyString())).thenReturn(reports);

        ResponseEntity<List<Report>> response = reportController.findReportsByUserId("userId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
        verify(reportService, times(1)).findReportsByUserId(anyString());
    }

    @Test
    void testFindReportsByAuthorId_Success() {
        List<Report> reports = new ArrayList<>();
        reports.add(new Report());
        reports.add(new Report());

        when(reportService.findReportsByAuthorId(anyString())).thenReturn(reports);

        ResponseEntity<List<Report>> response = reportController.findReportsByAuthorId("authorId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
        verify(reportService, times(1)).findReportsByAuthorId(anyString());
    }
}

