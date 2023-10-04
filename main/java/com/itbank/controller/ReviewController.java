package com.itbank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.itbank.model.MemberDTO;
import com.itbank.model.ReviewDTO;
import com.itbank.service.ReviewService;

@Controller
@RequestMapping("board/review")
public class ReviewController {
	
	@Autowired private ReviewService reviewService;
	
   //review 작성
  @PostMapping("/write/{idx}")		// form 태그에서 idx를 넘겨주는걸 쉽게 노출시키지 않기 위해서 pathVariable을 이용해서 보내준다
  public ModelAndView write(@PathVariable("idx")int idx, HttpSession session,ReviewDTO dto) {
	   ModelAndView mav = new ModelAndView("redirect:/board/view/" + idx);
	   MemberDTO login = (MemberDTO)session.getAttribute("login");
	   dto.setMember_idx(login.getIdx());
	   dto.setBoard_idx(idx);
	   int row = reviewService.reviewWrite(dto);
	   System.out.println(row + "행이 등록 되었습니다.");
	   return mav;
  }
   

	
}
