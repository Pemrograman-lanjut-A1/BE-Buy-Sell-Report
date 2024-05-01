package id.ac.ui.cs.advprog.besell.strategy;

import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemReportStrategy implements ReportStrategy {
    private ReportRepository reportRepository;

    public ItemReportStrategy(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> fetchReports(String itemId) {
        return reportRepository.findByTargetId(itemId);
    }
}