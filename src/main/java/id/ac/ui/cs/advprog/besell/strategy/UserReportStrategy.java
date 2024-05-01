package id.ac.ui.cs.advprog.besell.strategy;

import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.repository.ReportRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserReportStrategy implements ReportStrategy {

    private ReportRepository reportRepository;
    public UserReportStrategy(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }


    @Override
    public List<Report> fetchReports(String userId) {
        return reportRepository.findByTargetId(userId);
    }
}
