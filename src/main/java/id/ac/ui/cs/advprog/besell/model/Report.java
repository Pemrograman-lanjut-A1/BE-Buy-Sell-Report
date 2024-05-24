package id.ac.ui.cs.advprog.besell.model;

import id.ac.ui.cs.advprog.besell.enums.ReportTargetType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
public class Report {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;

    private String authorId;
    private String description;
    private LocalDateTime reportDate;
    private String targetId;
    private ReportTargetType targetType;

    public Report() {
    }
    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = description;
    }
    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }
    public static class Builder {
        private String authorId;
        private String description;
        private LocalDateTime reportDate;
        private String targetId;
        private ReportTargetType targetType;

        public Builder authorId(String authorId) {
            if (authorId == null || authorId.isEmpty()) {
                throw new IllegalArgumentException("AuthorId cannot be null or empty");
            }
            this.authorId = authorId;
            return this;
        }

        public Builder description(String description) {
            if (description == null || description.isEmpty()) {
                throw new IllegalArgumentException("Description cannot be null or empty");
            }
            this.description = description;
            return this;
        }

        public Builder reportDate(LocalDateTime reportDate) {
            this.reportDate = reportDate;
            return this;
        }

        public Builder targetId(String targetId) {
            if (targetId == null || targetId.isEmpty()) {
                throw new IllegalArgumentException("TargetId cannot be null or empty");
            }
            this.targetId = targetId;
            return this;
        }

        public Builder targetType(ReportTargetType targetType) {
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
            Report report = new Report();
            report.id = UUID.randomUUID().toString();
            report.authorId = this.authorId;
            report.description = this.description;
            report.reportDate = this.reportDate != null ? this.reportDate : LocalDateTime.now();
            report.targetId = this.targetId;
            report.targetType = this.targetType;
            return report;
        }
    }
}
