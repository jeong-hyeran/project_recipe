package com.itbank.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.itbank.model.BoardDTO;
import com.itbank.model.BoardLikeDTO;

@Repository
public interface BoardDAO {

	int insertBoard(BoardDTO dto);

	List<BoardDTO> selectAll();

	int maxIdx();

	BoardDTO selectOne(int idx);
	
	int boardUpdate(BoardDTO dto);

	int boardDelete(int idx);

	List<BoardDTO> search(String keyword);

	List<BoardDTO> excludeSearchUP(HashMap<String, String> map);

	List<BoardDTO> excludeSearch(String excludeKeyword);

	int viewCount(int idx);

	List<BoardDTO> searches(String searchKeyword);

	void boardLike(int idx);

	void boardHate(int idx);

	void like(int idx);

	void likeStatusUpdate(Map<String, Object> map);

	void dislike(int idx);

	BoardLikeDTO selectBoardLike(BoardLikeDTO dto);

	int insertBoardLike(BoardLikeDTO dto);

	BoardLikeDTO selectboardLikeByIdx(int idx);

	int updateLikeStatus(BoardLikeDTO dto);

	int updateLikeCountUp(int idx);

	int updateLikeCountDown(int idx);

	
	



}