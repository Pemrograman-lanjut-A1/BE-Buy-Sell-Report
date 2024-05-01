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
    void testFindReportById_NotFound() {
        when(reportRepository.findById("1")).thenReturn(Optional.empty());

        Optional<Report> report = reportService.findReportById("1");

        assertTrue(report.isEmpty());
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

}
