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
        report = new Report("eb558e9f-1c39-460e-8860-71af6af63bd6", authorId,
                "Barang ini tidak sesuai", dateCreated, targetId, ReportTargetType.ITEM);
        report.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(report.getId(), "eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(report.getDescription(), "Barang ini tidak sesuai");
        assertEquals(report.getAuthorId(), "13652556-012a-4c07-b546-54eb1396d79b");
        assertEquals(report.getReportDate(), dateCreated);
        assertEquals(report.getTargetId(), targetId);
        assertEquals(report.getTargetType(), ReportTargetType.ITEM);
    }

    @Test
    void testCreateReportWithoutDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            report = new Report("eb558e9f-1c39-460e-8860-71af6af63bd6", "13652556-012a-4c07-b546-54eb1396d79b",
                    "", LocalDateTime.now(), "a2c62328-4a37-4664-83c7-f32db8620155", ReportTargetType.ITEM);
        });
    }
    @Test
    void testSetReportId() {
        String idTest = "a2c62328-4a37-4664-83c7-f32db8620155";
        report = new Report("eb558e9f-1c39-460e-8860-71af6af63bd6", "13652556-012a-4c07-b546-54eb1396d79b",
                "Deskripsi report", LocalDateTime.now(), "a2c62328-4a37-4664-83c7-f32db8620155", ReportTargetType.ITEM);
        report.setId(idTest);
        assertEquals(report.getId(), idTest);
    }
    @Test
    void testSetAuthorId() {
        String authorIdTest = "a2c62328-4a37-4664-83c7-f32db8620155";
        report = new Report("eb558e9f-1c39-460e-8860-71af6af63bd6", "13652556-012a-4c07-b546-54eb1396d79b",
                "Deskripsi report", LocalDateTime.now(), "a2c62328-4a37-4664-83c7-f32db8620155", ReportTargetType.ITEM);
        report.setAuthorId(authorIdTest);
        assertEquals(report.getAuthorId(), authorIdTest);
    }

    @Test
    void testSetDeskripsiValid() {
        String deskripsiTest = "Deskripsi yang valid";
        report = new Report("eb558e9f-1c39-460e-8860-71af6af63bd6", "13652556-012a-4c07-b546-54eb1396d79b",
                "Deskripsi report", LocalDateTime.now(), "a2c62328-4a37-4664-83c7-f32db8620155", ReportTargetType.ITEM);
        report.setDescription(deskripsiTest);
        assertEquals(report.getDescription(), deskripsiTest);
    }

    @Test
    void testSetDeskripsiInvalid() {
        String deskripsiTest = "";
        report = new Report("eb558e9f-1c39-460e-8860-71af6af63bd6", "13652556-012a-4c07-b546-54eb1396d79b",
                "Deskripsi report", LocalDateTime.now(),"a2c62328-4a37-4664-83c7-f32db8620155", ReportTargetType.ITEM);
        assertThrows(IllegalArgumentException.class, () -> {
            report.setDescription(deskripsiTest);
        });
    }

    @Test
    void testEmptyAuthorId() {
        assertThrows(IllegalArgumentException.class, () -> {
            report = new Report("123", "", "deskripsi", LocalDateTime.now(), "4444", ReportTargetType.ITEM);
        });
    }

    @Test
    void testEmptyTargetId() {
        assertThrows(IllegalArgumentException.class, () -> {
            report = new Report("123", "142142", "deskripsi", LocalDateTime.now(), "", ReportTargetType.ITEM);
        });
    }
}
