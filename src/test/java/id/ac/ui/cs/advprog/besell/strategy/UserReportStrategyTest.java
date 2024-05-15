package id.ac.ui.cs.advprog.besell.strategy;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import id.ac.ui.cs.advprog.besell.enums.ReportTargetType;
import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class UserReportStrategyTest {
    @Mock
    private ReportRepository reportRepository;

    private UserReportStrategy userReportStrategy;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userReportStrategy = new UserReportStrategy(reportRepository);
    }

    @Test
    public void testFetchReports() {
        String userId = "123";
        List<Report> expectedReports = Collections.singletonList(new Report("222","345","desc", LocalDateTime.now(), userId, ReportTargetType.USER));
        when(reportRepository.findByTargetId(userId)).thenReturn(expectedReports);

        List<Report> reports = userReportStrategy.fetchReports(userId);

        assertNotNull(reports);
        assertEquals(1, reports.size());
        verify(reportRepository).findByTargetId(userId);
    }
}
