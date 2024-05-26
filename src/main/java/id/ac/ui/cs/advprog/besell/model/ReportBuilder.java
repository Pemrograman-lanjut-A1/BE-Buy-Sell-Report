package id.ac.ui.cs.advprog.besell.model;

import id.ac.ui.cs.advprog.besell.enums.ReportTargetType;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReportBuilder {
    private String authorId;
    private String description;
    private LocalDateTime reportDate;
    private String targetId;
    private ReportTargetType targetType;

    public ReportBuilder authorId(String authorId) {
        if (authorId == null || authorId.isEmpty()) {
            throw new IllegalArgumentException("AuthorId cannot be null or empty");
        }
        this.authorId = authorId;
        return this;
    }

    public ReportBuilder description(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.description = description;
        return this;
    }

    public ReportBuilder reportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public ReportBuilder targetId(String targetId) {
        if (targetId == null || targetId.isEmpty()) {
            throw new IllegalArgumentException("TargetId cannot be null or empty");
        }
        this.targetId = targetId;
        return this;
    }

    public ReportBuilder targetType(ReportTargetType targetType) {
        if (targetType == null) {
            throw new IllegalArgumentException("TargetType cannot be null");
        }
        this.targetType = targetType;
        return this;
    }

    public Report build() {
        if (authorId == null || description == null || targetId == null || targetType == null) {
            throw new IllegalStateException("Mandatory fields are not set");
        }
        return new Report(
                UUID.randomUUID().toString(),
                this.authorId,
                this.description,
                this.reportDate != null ? this.reportDate : LocalDateTime.now(),
                this.targetId,
                this.targetType
        );
    }
}
