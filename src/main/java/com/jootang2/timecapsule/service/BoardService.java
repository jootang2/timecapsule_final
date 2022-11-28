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
    private final CapsuleService capsuleService;

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
        board.setHit(0);
        boardRepository.save(board);
    }

    public Board findById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(null);
        return board;
    }

    public void update(Long boardId, BoardDto boardDto) {
        Board board = findById(boardId);
        board.setBoardCategory(boardDto.getBoardCategory());
        board.setBoardTitle(boardDto.getBoardTitle());
        board.setBoardContent(boardDto.getBoardContent());
        board.setPlaceX(boardDto.getPlaceX());
        board.setPlaceY(boardDto.getPlaceY());
        boardRepository.save(board);
    }

    public void delete(Long boardId) {
        Board board = findById(boardId);
        boardRepository.delete(board);
    }

    public void deleteByCapsule(Long capsuleId) {
        Capsule capsule = capsuleService.findById(capsuleId);
        List<Board> boardList = boardRepository.findByCapsule(capsule);
        for(Board board : boardList){
            boardRepository.delete(board);
        }
    }

    public void addHit(Board board) {
        System.out.println("조회수 증가");
        System.out.println(board.getHit());
        board.setHit(board.getHit()+1);
        boardRepository.save(board);
        System.out.println(board.getHit());
    }
}
