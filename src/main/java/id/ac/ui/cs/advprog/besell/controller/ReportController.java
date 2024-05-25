package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.enums.ReportTargetType;
import id.ac.ui.cs.advprog.besell.model.Report;
import id.ac.ui.cs.advprog.besell.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/report", produces = "application/json")
@CrossOrigin(origins = "*")
public class ReportController {
    @Autowired
    ReportService reportService;

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Report>> createReport(@RequestBody Report report) {
        return reportService.createReport(report)
                .thenApply(createdReport -> ResponseEntity.created(URI.create("/reports/" + createdReport.getId())).body(createdReport))
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof ResponseStatusException) {
                        throw (ResponseStatusException) ex.getCause();
                    } else {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex);
                    }
                });
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<ResponseEntity<Report>> updateReport(@PathVariable String id, @RequestBody Report reportDetails) {
        return reportService.updateReport(id, reportDetails)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof ResponseStatusException) {
                        throw (ResponseStatusException) ex.getCause();
                    } else {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex);
                    }
                });
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteReport(@PathVariable String id) {
        return reportService.deleteReport(id)
                .<ResponseEntity<Void>>thenApply(response -> ResponseEntity.noContent().build())
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof ResponseStatusException) {
                        throw (ResponseStatusException) ex.getCause();
                    } else {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex);
                    }
                });
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Optional<Report>>> findReportById(@PathVariable String id) {
        return reportService.findReportById(id)
                .thenApply(report -> report.isPresent()
                        ? new ResponseEntity<>(report, HttpStatus.OK)
                        : new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public CompletableFuture<ResponseEntity<List<Report>>> findAll() {
        return reportService.findAll()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/items/{itemId}")
    public CompletableFuture<ResponseEntity<List<Report>>> findReportsByItemId(@PathVariable String itemId) {
        return reportService.findReportsByItemId(itemId)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/users/{userId}")
    public CompletableFuture<ResponseEntity<List<Report>>> findReportsByUserId(@PathVariable String userId) {
        return reportService.findReportsByUserId(userId)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/authors/{authorId}")
    public CompletableFuture<ResponseEntity<List<Report>>> findReportsByAuthorId(@PathVariable String authorId) {
        return reportService.findReportsByAuthorId(authorId)
                .thenApply(ResponseEntity::ok);
    }
}