package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.common.util.file.FileUtil;
import com.kh.spring.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

	Logger logger = LoggerFactory.getLogger(getClass());
	private final BoardService boardService;
	
	@GetMapping("board-form")
	public void boadForm() {};
	
	@PostMapping("upload")
	public String uploadBoard(
			Board board
			,@RequestParam List<MultipartFile> files
			,@SessionAttribute("authentication") Member member
			) {
		
		board.setUserId(member.getUserId());
		boardService.insertBoard(files, board);
		return "redirect:/";
	}
}
