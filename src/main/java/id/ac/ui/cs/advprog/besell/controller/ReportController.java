package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/report", produces = "application/json")
@CrossOrigin(origins = "*")
public class ReportController {
    @Autowired
    ReportService reportService;
    @PostMapping("/create")
    public String createReport() {
        return "createReport";
    }
    @PostMapping("/update")
    public String updateReport() {
        return "updateReport";
    }
    @GetMapping("/get-all-report")
    public String getAllReport() {
        return "allReports";
    }
    @GetMapping("/get-report-by-id")
    public String getReportById() {
        return "reportById";
    }
    @DeleteMapping("/delete")
    public String deleteReport() {
        return "deleteReport";
    }

}
