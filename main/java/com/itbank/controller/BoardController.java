package com.itbank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.itbank.model.BoardDTO;
import com.itbank.model.BoardLikeDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.ReviewDTO;
import com.itbank.service.BoardService;
import com.itbank.service.ReviewService;


@Controller
@RequestMapping("/board")
public class BoardController {


	@Autowired private ReviewService reviewService;
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
	public ModelAndView view(@PathVariable("idx") int idx, HttpSession session) {
		ModelAndView mav = new ModelAndView("board/view");
		BoardDTO dto = boardService.selectOne(idx);
		
		// 좋아요 하트 모양 유지
		BoardLikeDTO likeDTO = new BoardLikeDTO();			// 데이터를 set해서 매개변수로 사용
		// 로그인된 정보를 가져옴
		MemberDTO login = (MemberDTO)session.getAttribute("login");
		// 로그인이 되어있으면
		if(login != null) {
			// BoardLikeDTO 자료형의 bL에다가 로그인된 아이디와 게시글의 idx를 set
			likeDTO.setMember_userid(login.getUserid());
			likeDTO.setBoard_idx(idx);
			// 게시글의 번호와 멤버의 아이디가 일치하는 BoardLike 데이터를 가져옴
			likeDTO = boardService.selectBoardLike(likeDTO);

			// board_like 테이블에는 글의 좋아요를 눌렀을 때 데이터가 insert되기 때문에
			// 좋아요를 한번도 누르지 않은 경우에는 result가 null 값이기 때문에 null 체크 조건을 걸어줌
			// 일치하는 정보가 있을때는 blike_status 정보를 가져와서 데이터를 보내줌
			if(likeDTO != null) {		
				mav.addObject("like_status", likeDTO.getBlike_status());
			}
		}
		
		List<String> contentList = boardService.getContentList(dto);
		List<String> fileNameList = boardService.getFileNameList(dto);
		List<ReviewDTO> re_list = reviewService.reviewSelectAll(idx);
	   
		mav.addObject("re_list",re_list);
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
	public ModelAndView update(BoardDTO dto) {
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

   
   // 검색 기능과 관련된 부분
   @PostMapping("/search")
   public ModelAndView search(String searchOption, String keyword) {
	   ModelAndView mav = new ModelAndView("board/searchList");
	   Map<String, Object> map = new HashMap<>();
	   String[] keywords = null;
	   List<BoardDTO> list = null;
	   
	   System.out.println("현재 검색어 : " + keyword);
	   // 만약 keyword가 ,를 포함하면(다중 검색이면)
	   if(keyword.contains(",")) {
		   keywords = keyword.split(",");
	   }
	   // 그렇지 않으면(키워드가 한개이면) 배열로 만들어주기 위해서
	   // 공백을 하나 넣어주고 그걸 기준으로 배열로 바꿈
	   else {			
		   keyword += " ";
		   keywords = keyword.split(" ");
	   }
	   // mapper.xml에서 조건을 나누기 위해서 선택한 옵션도 map에 넣어서 보내줌
	   map.put("searchOption", searchOption);
	   map.put("keywords", keywords);
	   list = boardService.search(map);

	   mav.addObject("list", list);
	   return mav;
   }
   
   
   // 좋아요 기능과 관련된 부분
   @GetMapping("like/{idx}/{member_userid}")
   public ModelAndView likeInsert(@PathVariable("idx")int idx, @PathVariable("member_userid") String member_userid) {
	   ModelAndView mav = new ModelAndView("board/likeResult");
	   BoardLikeDTO dto = new BoardLikeDTO();
	   dto.setBoard_idx(idx);
	   dto.setMember_userid(member_userid);

	   // 좋아요를 눌렀을 때 데이터가 있는지 없는지 확인 후 없으면 insert
	   // 있으면 있는 데이터에서 like_status를 가져옴
	   // 게시글 번호와 멤버의 아이디를 이용해 일치하는 데이터가 있는지 체크
	   BoardLikeDTO currentDTO = boardService.selectBoardLike(dto);		 
	   if(currentDTO == null) {				// 일치하는 데이터가 없으면
		   int row = boardService.insertLike(dto);		   		// insert 실행
		   System.out.println(row + "행이 추가되었습니다");
		   // insert 후 게시글 번호와 멤버의 아이디를 이용해 일치하는 데이터를 가져옴
		   currentDTO = boardService.selectBoardLike(dto);
	   }

	   // currentDTO에서 like_status 정보를 가져옴(즉, 반전되기 전의 상태)
	   String like_status = currentDTO.getBlike_status();
	   // like_status의 상태 반전
	   // board 테이블의 likeCount의 수 증가/감소
	   if(like_status.equals("false")) {
		   like_status = "true";
		   currentDTO.setBlike_status(like_status);		// 반전된 like_status의 데이터를 다시 set	
		   boardService.updateLikeCountUp(idx);			// 반전시킨 like_status의 값이 true라면 borad의 likeCount 수 증가
	   }	
	   else {
		   like_status = "false";
		   currentDTO.setBlike_status(like_status);		// 반전된 like_status의 데이터를 다시 set	
		   boardService.updateLikeCountDown(idx);		// 반전시킨 like_status의 값이 false라면 borad의 likeCount 수 감소
	   }
	   // 상태 반전을 시킨 걸로 board_like테이블의 like_status 데이터도 update해준다 
	   boardService.updateLikeStatus(currentDTO);		
	   mav.addObject("idx", idx);			// board/view 페이지를 보여주기 위한 board의 idx 데이터를 같이 보내줌
	   return mav;
   }
   

   
}























