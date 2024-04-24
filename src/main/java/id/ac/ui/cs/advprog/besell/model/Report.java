package id.ac.ui.cs.advprog.besell.model;

import id.ac.ui.cs.advprog.besell.enums.ReportTargetType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class Report {
    private String id;
    private String authorId;
    private String description;
    private LocalDateTime reportDate;
    private String targetId;
    private ReportTargetType targetType;

    public Report(String id, String authorId, String description,
                  LocalDateTime reportDate, String targetId, ReportTargetType targetType) {

    }
}
