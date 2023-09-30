package com.itbank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	   @PostMapping("/write")
	   public ModelAndView write(HttpSession session,ReviewDTO dto) {
		   ModelAndView mav = new ModelAndView("redirect:/board/view/" + dto.getBoard_idx());
		   MemberDTO login = (MemberDTO)session.getAttribute("login");
		   dto.setMember_idx(login.getIdx());
		   int row = reviewService.reviewWrite(dto);
		   System.out.println(row + "행이 등록 되었습니다.");
		   return mav;
	   }
	   

	
}
