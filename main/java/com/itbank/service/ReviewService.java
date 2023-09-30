package com.itbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbank.model.ReviewDTO;
import com.itbank.repository.ReviewDAO;

@Service
public class ReviewService {

	@Autowired private ReviewDAO reviewDAO;
	
	public int reviewWrite(ReviewDTO dto) {
		int row = reviewDAO.reviewWrite(dto);
		return row;
	}

	// board.idx를 받아서 댓글을 끌고오는 작업
	public List<ReviewDTO> reviewSelectAll(int idx) {
		List<ReviewDTO> list = reviewDAO.reviewSelectAll(idx);  
		return list;
	}


}
