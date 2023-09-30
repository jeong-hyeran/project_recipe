package com.itbank.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itbank.model.ReviewDTO;

@Repository
public interface ReviewDAO {

	int reviewWrite(ReviewDTO dto);

	List<ReviewDTO> reviewSelectAll(int idx);

}
