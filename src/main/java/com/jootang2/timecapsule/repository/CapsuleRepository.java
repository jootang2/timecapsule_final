package com.jootang2.timecapsule.repository;

import com.jootang2.timecapsule.domain.Capsule;
import com.jootang2.timecapsule.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapsuleRepository extends JpaRepository<Capsule, Long> {
    List<Capsule> findByUser(SiteUser user);
}
