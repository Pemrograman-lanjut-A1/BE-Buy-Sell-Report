package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ReportServiceImpl implements ReportService{
    @Autowired
    private ReportRepository reportRepository;

    @Async
    public CompletableFuture<Report> createReport(Report report) {
        report.setReportDate(LocalDateTime.now());
        Report savedReport = reportRepository.save(report);
        return CompletableFuture.completedFuture(savedReport);
    }

    @Async
    public CompletableFuture<Report> updateReport(String id, Report reportDetails) {
        return reportRepository.findById(id)
                .map(existingReport -> {
                    existingReport.setDescription(reportDetails.getDescription());
                    existingReport.setReportDate(LocalDateTime.now());
                    Report updatedReport = reportRepository.save(existingReport);
                    return CompletableFuture.completedFuture(updatedReport);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Report with id " + id + " not found"));
    }



    @Async
    public CompletableFuture<Void> deleteReport(String id) {
        reportRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }



    public CompletableFuture<Optional<Report>> findReportById(String id) {
        Optional<Report> report = reportRepository.findById(id);
        return CompletableFuture.completedFuture(report);
    }

    @Override
    public CompletableFuture<List<Report>> findAll() {
        List<Report> reports = reportRepository.findAll();
        return CompletableFuture.completedFuture(reports);
    }

    @Override
    public CompletableFuture<List<Report>> findReportsByItemId(String itemId) {
        List<Report> reports = reportRepository.findByTargetId(itemId);
        return CompletableFuture.completedFuture(reports);
    }

    @Override
    public CompletableFuture<List<Report>> findReportsByUserId(String userId) {
        List<Report> reports = reportRepository.findByTargetId(userId);
        return CompletableFuture.completedFuture(reports);
    }

    @Override
    public CompletableFuture<List<Report>> findReportsByAuthorId(String authorId) {
        List<Report> reports = reportRepository.findByAuthorId(authorId);
        return CompletableFuture.completedFuture(reports);
    }
}
