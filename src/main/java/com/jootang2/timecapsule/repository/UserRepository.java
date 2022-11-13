package com.jootang2.timecapsule.repository;

import com.jootang2.timecapsule.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByName(String userName);
}
