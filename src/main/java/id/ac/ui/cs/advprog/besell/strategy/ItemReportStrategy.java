package id.ac.ui.cs.advprog.besell.strategy;

import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.repository.ReportRepository;

import java.util.List;

public class ItemReportStrategy implements ReportStrategy {
    private ReportRepository reportRepository;

    public ItemReportStrategy(ReportRepository reportRepository) {

    }

    @Override
    public List<Report> execute(String itemId) {
        return null;
    }
}