package id.ac.ui.cs.advprog.besell.strategy;

import id.ac.ui.cs.advprog.besell.model.Report;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ReportStrategy {
    List<Report> fetchReports(String targetId);
}