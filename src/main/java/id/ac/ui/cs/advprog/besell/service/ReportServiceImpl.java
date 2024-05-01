package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.repository.ReportRepository;
import id.ac.ui.cs.advprog.besell.strategy.ReportContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService{
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ReportContext reportContext;

    public Report createReport(Report report) {
        reportRepository.save(report);
        return report;
    }


    public Report updateReport(String id, Report reportDetails) {
        return reportRepository.findById(id)
                .map(report -> {
                    report.setDescription(reportDetails.getDescription());
                    return reportRepository.save(report);
                })
                .orElseGet(() -> {
                    reportDetails.setId(id);
                    return reportRepository.save(reportDetails);
                });
    }



    public void deleteReport(String id) {
        reportRepository.deleteById(id);
    }

    public Optional<Report> findReportById(String id) {
        return reportRepository.findById(id);
    }

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> findReportsByItemId(String itemId) {
        return reportContext.loadReports(itemId);
    }

    @Override
    public List<Report> findReportsByUserId(String userId) {
        return reportContext.loadReports(userId);
    }

    @Override
    public List<Report> findReportsByAuthorId(String authorId) {
        return reportContext.loadReports(authorId);
    }
}
