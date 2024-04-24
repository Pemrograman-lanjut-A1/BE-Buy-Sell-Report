package id.ac.ui.cs.advprog.besell.strategy;

import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.repository.ReportRepository;

import java.util.List;

public class UserReportStrategy implements ReportStrategy {
    private ReportRepository reportRepository;

    public UserReportStrategy(ReportRepository reportRepository) {

    }

    @Override
    public List<Report> execute(String userId) {
        return null;
    }
}
