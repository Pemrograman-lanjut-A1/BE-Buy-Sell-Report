package id.ac.ui.cs.advprog.besell.strategy;

import id.ac.ui.cs.advprog.besell.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Component
public class ReportContext {
    private ReportStrategy reportStrategy;
    public void setReportStrategy(ReportStrategy reportStrategy) {
        this.reportStrategy = reportStrategy;
    }

    public List<Report> loadReports(String targetId) {
        return reportStrategy.fetchReports(targetId);
    }
}
