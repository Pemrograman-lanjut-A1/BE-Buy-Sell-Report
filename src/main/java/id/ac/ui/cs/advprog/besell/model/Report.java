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
    public Report(String id, String authorId, String description, LocalDateTime reportDate, String targetId, ReportTargetType targetType) {
        this.id = id;
        this.authorId = authorId;
        this.description = description;
        this.reportDate = reportDate;
        this.targetId = targetId;
        this.targetType = targetType;
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

}
