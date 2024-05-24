package id.ac.ui.cs.advprog.besell.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import id.ac.ui.cs.advprog.besell.strategy.ReportContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private ReportContext reportContext;

    @InjectMocks
    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateReport_Success() {
        Report report = new Report();
        when(reportRepository.save(any(Report.class))).thenReturn(report);

        Report createdReport = reportService.createReport(report);

        assertNotNull(createdReport);
        verify(reportRepository).save(report);
    }

    @Test
    void testUpdateReport_Found() {
        Report existingReport = new Report();
        existingReport.setId("1");
        existingReport.setDescription("Old Description");

        Report updatedDetails = new Report();
        updatedDetails.setDescription("New Description");

        when(reportRepository.findById("1")).thenReturn(Optional.of(existingReport));
        when(reportRepository.save(any(Report.class))).thenReturn(updatedDetails);

        Report updatedReport = reportService.updateReport("1", updatedDetails);

        assertNotNull(updatedReport);
        assertEquals("New Description", updatedReport.getDescription());
    }

    @Test
    void testUpdateReport_NotFound_CreateNew() {
        Report newReport = new Report();
        newReport.setId("1");
        newReport.setDescription("New Report");

        when(reportRepository.findById("1")).thenReturn(Optional.empty());
        when(reportRepository.save(any(Report.class))).thenReturn(newReport);

        Report createdReport = reportService.updateReport("1", newReport);

        assertNotNull(createdReport);
        assertEquals("New Report", createdReport.getDescription());
    }
    @Test
    public void testDeleteReport() {
        doNothing().when(reportRepository).deleteById("1");
        reportService.deleteReport("1");
        verify(reportRepository).deleteById("1");
    }
    @Test
    void testFindReportById_NotFound() {
        when(reportRepository.findById("1")).thenReturn(Optional.empty());

        Optional<Report> report = reportService.findReportById("1");

        assertTrue(report.isEmpty());
    }
    @Test
    public void testFindReportById() {
        Report report = new Report();
        report.setId("1");
        report.setDescription("Sample report");
        when(reportRepository.findById("1")).thenReturn(Optional.of(report));
        Optional<Report> foundReport = reportService.findReportById("1");
        assertTrue(foundReport.isPresent());
        assertEquals("Sample report", foundReport.get().getDescription());
    }
    @Test
    void testFindReportsByItemId() {
        Report report = new Report();
        when(reportContext.loadReports("item1")).thenReturn(Arrays.asList(report));

        List<Report> reports = reportService.findReportsByItemId("item1");

        assertFalse(reports.isEmpty());
        assertEquals(1, reports.size());
        verify(reportContext).loadReports("item1");
    }
    @Test
    public void testFindReportsByUserId() {
        Report report = new Report();
        report.setId("1");
        report.setDescription("Sample report");
        when(reportContext.loadReports("user1")).thenReturn(Arrays.asList(report));
        List<Report> reports = reportService.findReportsByUserId("user1");
        assertFalse(reports.isEmpty());
        assertEquals(1, reports.size());
    }
    @Test
    public void testFindReportsByAuthorId() {
        Report report = new Report();
        report.setId("1");
        report.setDescription("Sample report");
        when(reportContext.loadReports("author1")).thenReturn(Arrays.asList(report));
        List<Report> reports = reportService.findReportsByAuthorId("author1");
        assertFalse(reports.isEmpty());
        assertEquals(1, reports.size());
    }

    @Test
    void testFindAll_Empty() {
        when(reportRepository.findAll()).thenReturn(List.of());

        List<Report> reports = reportService.findAll();

        assertTrue(reports.isEmpty());
        verify(reportRepository, times(1)).findAll();
    }

    @Test
    void testFindReportsByItemId_NoReportsFound() {
        when(reportContext.loadReports(anyString())).thenReturn(List.of());

        List<Report> reports = reportService.findReportsByItemId("nonExistingItemId");

        assertTrue(reports.isEmpty());
        verify(reportContext, times(1)).loadReports(anyString());
    }

    @Test
    void testFindReportsByUserId_NoReportsFound() {
        when(reportContext.loadReports(anyString())).thenReturn(List.of());

        List<Report> reports = reportService.findReportsByUserId("nonExistingUserId");

        assertTrue(reports.isEmpty());
        verify(reportContext, times(1)).loadReports(anyString());
    }

    @Test
    void testFindReportsByAuthorId_NoReportsFound() {
        when(reportContext.loadReports(anyString())).thenReturn(List.of());

        List<Report> reports = reportService.findReportsByAuthorId("nonExistingAuthorId");

        assertTrue(reports.isEmpty());
        verify(reportContext, times(1)).loadReports(anyString());
    }
    @Test
    void testDeleteReport_NotFound() {
        doThrow(new IllegalArgumentException("Report not found")).when(reportRepository).deleteById(anyString());

        assertThrows(IllegalArgumentException.class, () -> reportService.deleteReport(UUID.randomUUID().toString()));
    }
}

