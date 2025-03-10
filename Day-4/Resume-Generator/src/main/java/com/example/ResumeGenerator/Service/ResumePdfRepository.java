package com.example.ResumeGenerator.Service;

import com.example.ResumeGenerator.ResumePdf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumePdfRepository extends JpaRepository<ResumePdf, Long> {
    ResumePdf findByResumeId(Long resumeId);
}
