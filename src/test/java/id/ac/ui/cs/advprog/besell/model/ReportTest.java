package id.ac.ui.cs.advprog.besell.model;

import id.ac.ui.cs.advprog.besell.enums.ReportTargetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReportTest {
    Report report;
    @Test
    void testCreateReportWithDescription() {
        String authorId = "13652556-012a-4c07-b546-54eb1396d79b";
        LocalDateTime dateCreated = LocalDateTime.now();
        String targetId = "a2c62328-4a37-4664-83c7-f32db8620155";
        report = new ReportBuilder().authorId(authorId).description("Barang ini tidak sesuai").reportDate(dateCreated).targetId(targetId)
                .targetType(ReportTargetType.ITEM).build();
        assertEquals(report.getDescription(), "Barang ini tidak sesuai");
        assertEquals(report.getAuthorId(), authorId);
        assertEquals(report.getReportDate(), dateCreated);
        assertEquals(report.getTargetId(), targetId);
        assertEquals(report.getTargetType(), ReportTargetType.ITEM);
    }

    @Test
    void testCreateReportWithoutDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            report = new ReportBuilder()
                    .authorId("13652556-012a-4c07-b546-54eb1396d79b")
                    .reportDate(LocalDateTime.now()).description("")
                    .targetId("a2c62328-4a37-4664-83c7-f32db8620155")
                    .targetType(ReportTargetType.ITEM)
                    .build();
        });
    }

    @Test
    void testSetDescriptionValid() {
        String descriptionTest = "Deskripsi yang valid";
        report = new ReportBuilder()
                .authorId("13652556-012a-4c07-b546-54eb1396d79b")
                .description("Deskripsi report")
                .reportDate(LocalDateTime.now())
                .targetId("a2c62328-4a37-4664-83c7-f32db8620155")
                .targetType(ReportTargetType.ITEM)
                .build();
        report.setDescription(descriptionTest);
        assertEquals(report.getDescription(), descriptionTest);
    }

    @Test
    void testSetDescriptionInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            report = new ReportBuilder()
                    .authorId("13652556-012a-4c07-b546-54eb1396d79b")
                    .description("Deskripsi report")
                    .reportDate(LocalDateTime.now())
                    .targetId("a2c62328-4a37-4664-83c7-f32db8620155")
                    .targetType(ReportTargetType.ITEM)
                    .build();
            report.setDescription("");
        });
    }

    @Test
    void testEmptyAuthorId() {
        assertThrows(IllegalArgumentException.class, () -> {
            report = new ReportBuilder()
                    .authorId("")
                    .description("deskripsi")
                    .reportDate(LocalDateTime.now())
                    .targetId("4444")
                    .targetType(ReportTargetType.ITEM)
                    .build();
        });
    }

    @Test
    void testEmptyTargetId() {
        assertThrows(IllegalArgumentException.class, () -> {
            report = new ReportBuilder()
                    .authorId("123")
                    .description("deskripsi")
                    .reportDate(LocalDateTime.now())
                    .targetId("")
                    .targetType(ReportTargetType.ITEM)
                    .build();
        });
    }

    @Test
    void testSetReportDate() {
        report = new ReportBuilder()
                .authorId("13652556-012a-4c07-b546-54eb1396d79b")
                .description("Deskripsi report")
                .reportDate(LocalDateTime.now())
                .targetId("a2c62328-4a37-4664-83c7-f32db8620155")
                .targetType(ReportTargetType.ITEM)
                .build();
        LocalDateTime newDate = LocalDateTime.now();
        report.setReportDate(newDate);
        assertEquals(report.getReportDate(), newDate);
    }
}
