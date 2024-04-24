package id.ac.ui.cs.advprog.besell.strategy;

import id.ac.ui.cs.advprog.besell.model.Report;

import java.util.List;

public interface ReportStrategy {
    List<Report> execute(String targetId);
}