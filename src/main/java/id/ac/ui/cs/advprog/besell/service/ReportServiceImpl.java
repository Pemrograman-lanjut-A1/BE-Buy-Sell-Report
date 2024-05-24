package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService{
    @Autowired
    private ReportRepository reportRepository;


    public Report createReport(Report report) {
        report.setReportDate(LocalDateTime.now());
        reportRepository.save(report);
        return report;
    }


    public Report updateReport(String id, Report reportDetails) {
        return reportRepository.findById(id)
                .map(existingReport -> {
                    existingReport.setDescription(reportDetails.getDescription());
                    existingReport.setReportDate(LocalDateTime.now());
                    return reportRepository.save(existingReport);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Report with id " + id + " not found"));
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
        return reportRepository.findByTargetId(itemId);
    }

    @Override
    public List<Report> findReportsByUserId(String userId) {
        return reportRepository.findByTargetId(userId);
    }

    @Override
    public List<Report> findReportsByAuthorId(String authorId) {
        return reportRepository.findByAuthorId(authorId);
    }
}
