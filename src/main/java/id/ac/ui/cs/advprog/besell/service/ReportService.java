package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Report;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ReportService {
    CompletableFuture<Report> createReport(Report report);
    CompletableFuture<Report> updateReport(String id, Report reportDetails);
    CompletableFuture<Void> deleteReport(String id);
    CompletableFuture<Optional<Report>> findReportById(String id);
    CompletableFuture<List<Report>> findAll();
    CompletableFuture<List<Report>> findReportsByItemId(String itemId);
    CompletableFuture<List<Report>> findReportsByUserId(String userId);
    CompletableFuture<List<Report>> findReportsByAuthorId(String authorId);
}
