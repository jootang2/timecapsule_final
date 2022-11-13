package com.jootang2.timecapsule.repository;

import com.jootang2.timecapsule.domain.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
}
