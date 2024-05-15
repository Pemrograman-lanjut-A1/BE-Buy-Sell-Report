package id.ac.ui.cs.advprog.besell.model;

import id.ac.ui.cs.advprog.besell.enums.ReportTargetType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Report {
    @Id
    private String id;
    private String authorId;
    private String description;
    private LocalDateTime reportDate;
    private String targetId;
    private ReportTargetType targetType;

    public Report(String id, String authorId, String description,
                  LocalDateTime reportDate, String targetId, ReportTargetType targetType) {
        this.id = UUID.randomUUID().toString();
        setAuthorId(authorId);
        setDescription(description);
        setReportDate(LocalDateTime.now());
        setTargetId(targetId);
        this.targetType = targetType;
    }

    public Report() {
        this.id = UUID.randomUUID().toString();
    }

    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = description;
    }

    public void setAuthorId(String authorId) {
        if (authorId == null || authorId.isEmpty()) {
            throw new IllegalArgumentException("Null authorId");
        }
        this.authorId = authorId;
    }

    public void setTargetId(String targetId) {
        if (targetId == null || targetId.isEmpty()) {
            throw new IllegalArgumentException("Null targetId");
        }
        this.targetId = targetId;
    }
}
