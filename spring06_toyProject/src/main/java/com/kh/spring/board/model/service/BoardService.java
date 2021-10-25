package com.kh.spring.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.board.model.repository.BoardRepository;
import com.kh.spring.common.util.file.FileUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void insertBoard(List<MultipartFile> multiparts, Board board) {
		boardRepository.insertBoard(board);
		
		FileUtil fileUtil = new FileUtil();
		for (MultipartFile multipartFile : multiparts) {
			boardRepository.insertFileInfo(fileUtil.fileUpload(multipartFile));
		}
	}
	
}
