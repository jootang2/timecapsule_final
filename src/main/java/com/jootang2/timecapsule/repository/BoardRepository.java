package com.jootang2.timecapsule.repository;

import com.jootang2.timecapsule.domain.Board;
import com.jootang2.timecapsule.domain.Capsule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByCapsule(Capsule capsule);

}
