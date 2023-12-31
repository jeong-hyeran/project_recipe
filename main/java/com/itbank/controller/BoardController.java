package com.itbank.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itbank.model.BoardDTO;
import com.itbank.model.MemberDTO;
import com.itbank.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired private BoardService boardService;
	
	@GetMapping("/write")
	public void write() {}
	
	@PostMapping("/write")
	public String write(BoardDTO dto, HttpSession session) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		if(login != null) {
			dto.setMember_idx(login.getIdx());
		}
		int idx = boardService.insertBoard(dto);
		System.out.println(idx + "번 글이 추가되었습니다.");
		return "redirect:/board/view/"+idx;
	}
	
	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		List<BoardDTO> list = boardService.selectAll();
		mav.addObject("list",list);
		return mav;
	}
	
	@GetMapping("/view/{idx}")
	public ModelAndView view(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("board/view");
		BoardDTO dto = boardService.selectOne(idx);
		
		List<String> contentList = boardService.getContentList(dto);
		List<String> fileNameList = boardService.getFileNameList(dto);
		
		mav.addObject("dto",dto);
		mav.addObject("contentList",contentList);
		mav.addObject("fileNameList",fileNameList);
		return mav;
	}
	
	@GetMapping("/update/{idx}")
	public ModelAndView update(@PathVariable("idx")int idx) {
		ModelAndView mav = new ModelAndView("board/update");
		BoardDTO dto = boardService.selectOne(idx);
		
		List<String> contentList = boardService.getContentList(dto);
		List<String> fileNameList = boardService.getFileNameList(dto);
		mav.addObject("dto", dto);
		mav.addObject("contentList", contentList);
		mav.addObject("fileNameList", fileNameList);
		return mav;
	}
	
	@PostMapping("/update/{idx}")
	public ModelAndView update(@PathVariable("idx") int idx, BoardDTO dto) {
		ModelAndView mav = new ModelAndView("redirect:/board/view/{idx}");
		int row = boardService.boardUpdate(dto);
		System.out.println(row + "행이 업데이트 되었습니다");
		return mav;
	}
	
	@GetMapping("delete/{idx}")
	public String delete(@PathVariable("idx")int idx) {
		BoardDTO dto = boardService.selectOne(idx);
		int row = boardService.boardDelete(dto);
		System.out.println(row + "행이 삭제되었습니다");
		return "redirect:/board/list";
	}
	
   @GetMapping("/boardDelete/{idx}")
   public String boardDelete(@PathVariable("idx")int idx) {
      BoardDTO dto = boardService.selectOne(idx);
      int row = boardService.boardDelete(dto);
      System.out.println(row + "행이 삭제되었습니다");
      return "redirect:/member/mypage";
   }

   // 검색 옵션 
   @PostMapping("/search")
   public ModelAndView search(String searchOption, String keyword){
	   ModelAndView mav = new ModelAndView("board/searchList");
	   List<BoardDTO> list = new ArrayList<BoardDTO>(); 
	   // boardDTO를 담을 list선언
	   if ("keyword".equals(searchOption)) {
		   // searchOption의 값이 keyword(포함하는 검색)이면 
		   list = boardService.search(keyword);
		   // search 메서드 실행
		   mav.addObject("keyword",keyword);
		   // 포함하는 검색어를 mav에 담기
	   } else if ("excludeKeyword".equals(searchOption)) {
		   // searchOption의 값이 excludeKeyword(제외하는 검색)이면 
		   list = boardService.excludeSearch(keyword);
		   // excluedSearch 메서드 실행
		   mav.addObject("excludeKeyword",keyword);
		   // 제외하는 검색어를 mav에 담기
	   }
	   mav.addObject("searchOption",searchOption);
	   // searchList.jsp if문으로 검색키워드 띄우기 위해 보내줌
	   mav.addObject("list",list);
	   return mav;
   }
   
//   // 여러개 검색 옵션 
//   @PostMapping("/search")
//   public ModelAndView search(@RequestParam("searchOption")String searchOption, @RequestParam("keyword")String keyword){
//	   ModelAndView mav = new ModelAndView("board/searchList");
//	   List<BoardDTO> list = new ArrayList<BoardDTO>(); 
//	   if ("keyword".equals(searchOption)) {
//		   list = boardService.searches(keyword);
//		   mav.addObject("keyword",keyword);
//	   } else if ("excludeKeyword".equals(searchOption)) {
//		   list = boardService.excludeSearch(keyword);
//		   mav.addObject("excludeKeyword",keyword);
//	   }
//	   mav.addObject("searchOption",searchOption);
//	   mav.addObject("list",list);
//	   return mav;
//   }
   

	// 포함한 검색 한 이후 제외할 검색어가 있을때
	@PostMapping("/searchList")
	public ModelAndView excludeSearch(String keyword, String excludeKeyword) {
		ModelAndView mav = new ModelAndView("board/searchList");
		List<BoardDTO> list = boardService.excludeSearchUP(keyword, excludeKeyword);
		// 쿼리문 실행
		System.out.println("포함 : "+ keyword + ", 제외 : " + excludeKeyword);
		mav.addObject("list",list);
		mav.addObject("excludeKeyword",excludeKeyword);
		mav.addObject("keyword",keyword);
		return mav;
	}
}