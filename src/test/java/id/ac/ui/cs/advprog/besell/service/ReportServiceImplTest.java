package id.ac.ui.cs.advprog.besell.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import id.ac.ui.cs.advprog.besell.enums.ReportTargetType;
import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;


    @InjectMocks
    private ReportServiceImpl reportService;

    private final String authorId = UUID.randomUUID().toString();
    private final String targetId = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReport_Success() {
        Report report = new Report.Builder()
                .authorId(authorId)
                .description("description")
                .targetId(targetId)
                .targetType(ReportTargetType.ITEM)
                .reportDate(LocalDateTime.now())
                .build();

        when(reportRepository.save(any(Report.class))).thenReturn(report);

        Report createdReport = reportService.createReport(report);

        assertNotNull(createdReport);
        verify(reportRepository).save(report);
    }

    @Test
    void testUpdateReport_Found() {
        Report existingReport = new Report.Builder()
                .authorId(authorId)
                .description("Old Description")
                .reportDate(LocalDateTime.now())
                .targetId(targetId)
                .targetType(ReportTargetType.ITEM)
                .build();

        Report updatedDetails = new Report.Builder()
                .authorId(authorId)
                .description("New Description")
                .reportDate(LocalDateTime.now())
                .targetId(targetId)
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportRepository.findById(existingReport.getId())).thenReturn(Optional.of(existingReport));
        when(reportRepository.save(any(Report.class))).thenReturn(existingReport);

        existingReport.setDescription(updatedDetails.getDescription());
        existingReport.setReportDate(updatedDetails.getReportDate());

        Report updatedReport = reportService.updateReport(existingReport.getId(), updatedDetails);

        assertNotNull(updatedReport);
        assertEquals("New Description", updatedReport.getDescription());
    }

    @Test
    void testUpdateReport_NotFound() {
        Report newReport = new Report.Builder()
                .authorId(authorId)
                .description("New Report")
                .reportDate(LocalDateTime.now())
                .targetId(targetId)
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportRepository.findById(newReport.getId())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> {
            reportService.updateReport(newReport.getId(), newReport);
        });
    }


    @Test
    public void testDeleteReport() {
        doNothing().when(reportRepository).deleteById("a2c62328-4a37-4664-83c7-f32db8620155");
        reportService.deleteReport("a2c62328-4a37-4664-83c7-f32db8620155");
        verify(reportRepository).deleteById("a2c62328-4a37-4664-83c7-f32db8620155");
    }

    @Test
    void testFindReportById_NotFound() {
        when(reportRepository.findById("a2c62328-4a37-4664-83c7-f32db8620155")).thenReturn(Optional.empty());

        Optional<Report> report = reportService.findReportById("a2c62328-4a37-4664-83c7-f32db8620155");

        assertTrue(report.isEmpty());
    }

    @Test
    public void testFindReportById() {
        Report report = new Report.Builder()
                .authorId(authorId)
                .description("Sample report")
                .reportDate(LocalDateTime.now())
                .targetId(targetId)
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportRepository.findById(report.getId())).thenReturn(Optional.of(report));
        Optional<Report> foundReport = reportService.findReportById(report.getId());
        assertTrue(foundReport.isPresent());
        assertEquals("Sample report", foundReport.get().getDescription());
    }

    @Test
    void testFindReportsByItemId() {
        Report report = new Report.Builder()
                .authorId(authorId)
                .description("Sample report")
                .reportDate(LocalDateTime.now())
                .targetId(targetId)
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportRepository.findByTargetId(targetId)).thenReturn(Arrays.asList(report));

        List<Report> reports = reportService.findReportsByItemId(targetId);

        assertFalse(reports.isEmpty());
        assertEquals(1, reports.size());
        verify(reportRepository).findByTargetId(targetId);
    }

    @Test
    public void testFindReportsByUserId() {
        Report report = new Report.Builder()
                .authorId(authorId)
                .description("Sample report")
                .reportDate(LocalDateTime.now())
                .targetId(targetId)
                .targetType(ReportTargetType.USER)
                .build();

        when(reportRepository.findByTargetId(targetId)).thenReturn(Arrays.asList(report));
        List<Report> reports = reportService.findReportsByUserId(targetId);
        assertFalse(reports.isEmpty());
        assertEquals(1, reports.size());
    }

    @Test
    public void testFindReportsByAuthorId() {
        Report report = new Report.Builder()
                .authorId(authorId)
                .description("Sample report")
                .reportDate(LocalDateTime.now())
                .targetId(targetId)
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportRepository.findByAuthorId(authorId)).thenReturn(Arrays.asList(report));
        List<Report> reports = reportService.findReportsByAuthorId(authorId);
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
        when(reportRepository.findByTargetId(anyString())).thenReturn(List.of());

        List<Report> reports = reportService.findReportsByItemId("nonExistingItemId");

        assertTrue(reports.isEmpty());
        verify(reportRepository, times(1)).findByTargetId(anyString());
    }

    @Test
    void testFindReportsByUserId_NoReportsFound() {
        when(reportRepository.findByTargetId(anyString())).thenReturn(List.of());

        List<Report> reports = reportService.findReportsByUserId("nonExistingUserId");

        assertTrue(reports.isEmpty());
        verify(reportRepository, times(1)).findByTargetId(anyString());
    }

    @Test
    void testFindReportsByAuthorId_NoReportsFound() {
        when(reportRepository.findByAuthorId(anyString())).thenReturn(List.of());

        List<Report> reports = reportService.findReportsByAuthorId("nonExistingAuthorId");

        assertTrue(reports.isEmpty());
        verify(reportRepository, times(1)).findByAuthorId(anyString());
    }

    @Test
    void testDeleteReport_NotFound() {
        doThrow(new IllegalArgumentException("Report not found")).when(reportRepository).deleteById(anyString());

        assertThrows(IllegalArgumentException.class, () -> reportService.deleteReport(UUID.randomUUID().toString()));
    }
}

