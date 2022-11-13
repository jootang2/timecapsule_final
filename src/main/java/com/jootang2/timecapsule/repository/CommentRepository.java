package com.jootang2.timecapsule.repository;

import com.jootang2.timecapsule.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
