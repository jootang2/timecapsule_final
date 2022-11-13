package com.jootang2.timecapsule.service;

import com.jootang2.timecapsule.domain.Board;
import com.jootang2.timecapsule.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

}
