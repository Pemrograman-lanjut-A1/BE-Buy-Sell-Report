package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/report", produces = "application/json")
@CrossOrigin(origins = "*")
public class ReportController {
    @Autowired
    ReportService reportService;
    @PostMapping("/create")
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        return new ResponseEntity<>(reportService.createReport(report), HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable String id, @RequestBody Report reportDetails) {
        return ResponseEntity.ok(reportService.updateReport(id, reportDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable String id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Report>> findReportById(@PathVariable String id) {
        Optional<Report> report = reportService.findReportById(id);
        if (report.isPresent()) {
            return new ResponseEntity<>(report, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Report>> findAllReports() {
        return ResponseEntity.ok(reportService.findAll());
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<List<Report>> findReportsByItemId(@PathVariable String itemId) {
        return ResponseEntity.ok(reportService.findReportsByItemId(itemId));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Report>> findReportsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(reportService.findReportsByUserId(userId));
    }

    @GetMapping("/authors/{authorId}")
    public ResponseEntity<List<Report>> findReportsByAuthorId(@PathVariable String authorId) {
        return ResponseEntity.ok(reportService.findReportsByAuthorId(authorId));
    }

}
