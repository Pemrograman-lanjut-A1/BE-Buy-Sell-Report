package id.ac.ui.cs.advprog.besell.repository;

import id.ac.ui.cs.advprog.besell.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    public List<Report> findByTargetId(String id);
    public List<Report> findByAuthorId(String id);
}