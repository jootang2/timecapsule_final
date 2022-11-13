package com.jootang2.timecapsule.service;

import com.jootang2.timecapsule.domain.Board;
import com.jootang2.timecapsule.domain.Capsule;
import com.jootang2.timecapsule.domain.SiteUser;
import com.jootang2.timecapsule.dto.BoardDto;
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

    public void create(BoardDto boardDto , Capsule capsule, SiteUser user) {
        Board board = new Board();
        board.setCapsule(capsule);
        board.setUser(user);
        board.setBoardCategory(boardDto.getBoardCategory());
        board.setBoardTitle(boardDto.getBoardTitle());
        board.setBoardContent(boardDto.getBoardContent());
        board.setPlaceX(boardDto.getPlaceX());
        board.setPlaceY(boardDto.getPlaceY());
        boardRepository.save(board);
    }

    public Board findById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(null);
        return board;
    }

}
