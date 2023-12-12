package com.lec.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lec.domain.BoardVO;

@RestController
public class BoardController {

	public BoardController() {
		System.out.println("===> BoardController 객체 생성!!!");
	}
	
	@GetMapping("/hello")
	public String hello(String name) {
		return name + "님 환영합니다!";
	}

	@GetMapping("/getBoard")
	public BoardVO getBoard() {
		BoardVO board = new BoardVO();
		
		board.setSeq(1);
		board.setTitle("테스트제목....");
		board.setWriter("작성자");
		board.setContent("테스트 내용입니다.........");
		board.setCreateDate(new Date());
		board.setCnt(0);
		
		return board;
	}	
	
	@GetMapping("/getBoardList")
	public List<BoardVO> getBoardList() {
		
		List<BoardVO> boardList = new ArrayList<>();
		
		for(int i=1;i<=10;i++) {
			BoardVO board = new BoardVO();
			board.setSeq(1);
			board.setTitle("테스트제목....");
			board.setWriter("작성자");
			board.setContent("테스트 내용입니다.........");
			board.setCreateDate(new Date());
			board.setCnt(0);
			boardList.add(board);
		}
		
		return boardList;
	}	
}