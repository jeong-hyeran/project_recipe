package com.itbank.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itbank.model.MemberDTO;
import com.itbank.model.ReviewDTO;
import com.itbank.service.ReviewService;


@Controller
@RequestMapping("/board/review")
public class ReviewController {
	
	@Autowired private ReviewService reviewService;
	
	   //review 작성
	   @PostMapping("/write/{idx}")
	   public ModelAndView write(@PathVariable("idx") int idx, HttpSession session,ReviewDTO dto) {
		   ModelAndView mav = new ModelAndView("redirect:/board/view/" + idx);
		   MemberDTO login = (MemberDTO)session.getAttribute("login");
		   dto.setBoard_idx(idx);
		   dto.setMember_idx(login.getIdx());
		   dto.setMember_fileName(login.getFileName());
		   int row = reviewService.reviewWrite(dto);
		   System.out.println(row + "행이 등록 되었습니다.");
		   return mav;
	   }
	   
	   @PostMapping("/modifyReview")
	   @ResponseBody
	   // ajax를 사용하기 위한 어노테이션, 이게 있어야 ajax의 return을 할 수 있다.
	   public String modify(@RequestParam Map<String, Object> param) {
		   // ajax로 data에 넣은 값을 매개변수로 불러온다.
		   // map의 key을 넣어 value값을 불러온다.
		   String result = reviewService.modifyReview(param);
		   if(result.equals("success")) {
			   return "success";
		   }else {
			   return "fail"; 
		   }		   
	   }

	   @PostMapping("/delete")
	   @ResponseBody
	   // ajax를 사용하기 위한 어노테이션, 이게 있어야 ajax의 return을 할 수 있다.
	   public String delete(@RequestParam Map<String, Object> param) {
		   int idx =Integer.parseInt((String)param.get("idx"));
		   String result = reviewService.delete(idx);
		   if(result.equals("success")) {
			   return "success";
		   }else {
			   return "fail"; 
		   }
	   }
	
   }
	   
	   
	

