package com.jootang2.timecapsule.repository;

import com.jootang2.timecapsule.domain.Capsule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapsuleRepository extends JpaRepository<Capsule, Long> {
}
