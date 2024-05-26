package id.ac.ui.cs.advprog.besell.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import id.ac.ui.cs.advprog.besell.enums.ReportTargetType;
import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.model.ReportBuilder;
import id.ac.ui.cs.advprog.besell.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

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
    void testCreateReport_Success() throws ExecutionException, InterruptedException {
        Report report = new ReportBuilder()
                .authorId(authorId)
                .description("description")
                .targetId(targetId)
                .targetType(ReportTargetType.ITEM)
                .reportDate(LocalDateTime.now())
                .build();

        when(reportRepository.save(any(Report.class))).thenReturn(report);

        CompletableFuture<Report> future = reportService.createReport(report);
        Report createdReport = future.get();

        assertNotNull(createdReport);
        verify(reportRepository).save(report);
    }

    @Test
    void testUpdateReport_Found() throws ExecutionException, InterruptedException {
        Report existingReport = new ReportBuilder()
                .authorId(authorId)
                .description("Old Description")
                .reportDate(LocalDateTime.now())
                .targetId(targetId)
                .targetType(ReportTargetType.ITEM)
                .build();

        Report updatedDetails = new ReportBuilder()
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

        CompletableFuture<Report> future = reportService.updateReport(existingReport.getId(), updatedDetails);
        Report updatedReport = future.get();

        assertNotNull(updatedReport);
        assertEquals("New Description", updatedReport.getDescription());
    }
    @Test
    void testUpdateReport_NotFound() {
        String nonExistentId = UUID.randomUUID().toString();

        Report reportDetails = new ReportBuilder()
                .authorId("authorId")
                .description("Description")
                .reportDate(LocalDateTime.now())
                .targetId("targetId")
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> reportService.updateReport(nonExistentId, reportDetails).get());
    }

    @Test
    public void testDeleteReport() throws ExecutionException, InterruptedException {
        // Create a new report
        Report report = new ReportBuilder()
                .authorId("a2c62328-4a37-4664-83c7-f32db8620155")
                .description("description")
                .targetId("13652556-012a-4c07-b546-54eb1396d79b")
                .reportDate(LocalDateTime.now())
                .targetType(ReportTargetType.ITEM)
                .build();

        // Save the report in the repository
        when(reportRepository.save(any(Report.class))).thenReturn(report);

        // Create the report asynchronously
        CompletableFuture<Report> createReportFuture = reportService.createReport(report);
        Report createdReport = createReportFuture.get();
        // Delete the report
        CompletableFuture<Void> deleteReportFuture = reportService.deleteReport(createdReport.getId());
        deleteReportFuture.get();

        // Verify that the report was deleted by checking if deleteById was called with the correct ID
        verify(reportRepository).deleteById(createdReport.getId());
    }

    @Test
    void testFindReportById_NotFound() throws ExecutionException, InterruptedException {
        when(reportRepository.findById("a2c62328-4a37-4664-83c7-f32db8620155")).thenReturn(Optional.empty());

        CompletableFuture<Optional<Report>> future = reportService.findReportById("a2c62328-4a37-4664-83c7-f32db8620155");
        Optional<Report> report = future.get();

        assertTrue(report.isEmpty());
    }

    @Test
    public void testFindReportById() throws ExecutionException, InterruptedException {
        Report report = new ReportBuilder()
                .authorId(authorId)
                .description("Sample report")
                .reportDate(LocalDateTime.now())
                .targetId(targetId)
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportRepository.findById(report.getId())).thenReturn(Optional.of(report));

        CompletableFuture<Optional<Report>> future = reportService.findReportById(report.getId());
        Optional<Report> foundReport = future.get();

        assertTrue(foundReport.isPresent());
        assertEquals("Sample report", foundReport.get().getDescription());
    }

    @Test
    void testFindReportsByItemId() throws ExecutionException, InterruptedException {
        Report report = new ReportBuilder()
                .authorId(authorId)
                .description("Sample report")
                .reportDate(LocalDateTime.now())
                .targetId(targetId)
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportRepository.findByTargetId(targetId)).thenReturn(Arrays.asList(report));

        CompletableFuture<List<Report>> future = reportService.findReportsByItemId(targetId);
        List<Report> reports = future.get();

        assertFalse(reports.isEmpty());
        assertEquals(1, reports.size());
        verify(reportRepository).findByTargetId(targetId);
    }

    @Test
    public void testFindReportsByUserId() throws ExecutionException, InterruptedException {
        Report report = new ReportBuilder()
                .authorId(authorId)
                .description("Sample report")
                .reportDate(LocalDateTime.now())
                .targetId(targetId)
                .targetType(ReportTargetType.USER)
                .build();

        when(reportRepository.findByTargetId(targetId)).thenReturn(Arrays.asList(report));

        CompletableFuture<List<Report>> future = reportService.findReportsByUserId(targetId);
        List<Report> reports = future.get();

        assertFalse(reports.isEmpty());
        assertEquals(1, reports.size());
    }

    @Test
    public void testFindReportsByAuthorId() throws ExecutionException, InterruptedException {
        Report report = new ReportBuilder()
                .authorId(authorId)
                .description("Sample report")
                .reportDate(LocalDateTime.now())
                .targetId(targetId)
                .targetType(ReportTargetType.ITEM)
                .build();

        when(reportRepository.findByAuthorId(authorId)).thenReturn(Arrays.asList(report));

        CompletableFuture<List<Report>> future = reportService.findReportsByAuthorId(authorId);
        List<Report> reports = future.get();

        assertFalse(reports.isEmpty());
        assertEquals(1, reports.size());
    }

    @Test
    void testFindAll_Empty() throws ExecutionException, InterruptedException {
        when(reportRepository.findAll()).thenReturn(List.of());

        CompletableFuture<List<Report>> future = reportService.findAll();
        List<Report> reports = future.get();

        assertTrue(reports.isEmpty());
        verify(reportRepository, times(1)).findAll();
    }

    @Test
    void testFindReportsByItemId_NoReportsFound() throws ExecutionException, InterruptedException {
        when(reportRepository.findByTargetId(anyString())).thenReturn(List.of());

        CompletableFuture<List<Report>> future = reportService.findReportsByItemId("nonExistingItemId");
        List<Report> reports = future.get();

        assertTrue(reports.isEmpty());
        verify(reportRepository, times(1)).findByTargetId(anyString());
    }

    @Test
    void testFindReportsByUserId_NoReportsFound() throws ExecutionException, InterruptedException {
        when(reportRepository.findByTargetId(anyString())).thenReturn(List.of());

        CompletableFuture<List<Report>> future = reportService.findReportsByUserId("nonExistingUserId");
        List<Report> reports = future.get();

        assertTrue(reports.isEmpty());
        verify(reportRepository, times(1)).findByTargetId(anyString());
    }

    @Test
    void testFindReportsByAuthorId_NoReportsFound() throws ExecutionException, InterruptedException {
        when(reportRepository.findByAuthorId(anyString())).thenReturn(List.of());

        CompletableFuture<List<Report>> future = reportService.findReportsByAuthorId("nonExistingAuthorId");
        List<Report> reports = future.get();

        assertTrue(reports.isEmpty());
        verify(reportRepository, times(1)).findByAuthorId(anyString());
    }

    @Test
    void testDeleteReport_NotFound() {
        String nonExistentId = UUID.randomUUID().toString();
        doThrow(EmptyResultDataAccessException.class).when(reportRepository).deleteById(nonExistentId);

        assertThrows(EmptyResultDataAccessException.class, () -> reportService.deleteReport(nonExistentId).get());
    }


}

