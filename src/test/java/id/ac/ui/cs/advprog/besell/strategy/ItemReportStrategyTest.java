package id.ac.ui.cs.advprog.besell.strategy;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import id.ac.ui.cs.advprog.besell.enums.ReportTargetType;
import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.repository.ReportRepository;
import id.ac.ui.cs.advprog.besell.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class ItemReportStrategyTest {

    @Mock
    private ReportRepository reportRepository;

    private ItemReportStrategy itemReportStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        itemReportStrategy = new ItemReportStrategy(reportRepository);
    }

    @Test
    void testFetchReports() {
        String itemId = "a2c62328-4a37-4664-83c7-f32db8620155";
        List<Report> mockReports = Arrays.asList(new Report("123","345","desc", LocalDateTime.now(), itemId, ReportTargetType.ITEM), new Report("123","345","desc", LocalDateTime.now(), itemId, ReportTargetType.ITEM));
        when(reportRepository.findReportsByItemId(itemId)).thenReturn(mockReports);

        List<Report> reports = itemReportStrategy.execute(itemId);

        assertNotNull(reports);
        assertEquals(2, reports.size());
        verify(reportRepository).findReportsByItemId(itemId);
    }
}
