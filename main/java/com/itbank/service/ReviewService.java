package com.itbank.service;

import java.util.List;
import java.util.Map;

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

	public String modifyReview(Map<String, Object> param) {
		   String content = (String)param.get("content");
		   int idx =Integer.parseInt((String)param.get("idx"));
		   ReviewDTO dto = new ReviewDTO();
		   dto.setIdx(idx);
		   dto.setRe_content(content);
		   reviewDAO.update(dto);
		try {
			return "success";
		}catch(Exception e) {
			e.printStackTrace();
			return "fail";
		}
		
	}

	public String delete(int idx) {
		reviewDAO.delete(idx);
		try {
			return "success";
		}catch(Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}


}
