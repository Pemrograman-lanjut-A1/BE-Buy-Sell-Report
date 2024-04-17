package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Report;

import java.util.List;

public interface ReportService {
    public Report createReport();
    public Report updateReport();
    public Report deleteReport();
    public Report getReportById();
    public List<Report> findAll();
}
