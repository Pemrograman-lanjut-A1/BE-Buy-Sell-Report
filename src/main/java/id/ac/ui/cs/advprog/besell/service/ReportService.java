package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Report;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    public Report createReport(Report report);
    public Report updateReport(String id, Report report);
    public void deleteReport(String id);
    public Optional<Report> findReportById(String id);
    public List<Report> findAll();
    public List<Report> findReportsByItemId(String itemId);
    public List<Report> findReportsByUserId(String userId);
    public List<Report> findReportsByAuthorId(String authorId);
}
