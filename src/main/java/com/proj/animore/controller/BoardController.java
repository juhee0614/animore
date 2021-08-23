package com.proj.animore.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proj.animore.dto.BoardDTO;
import com.proj.animore.dto.BoardReqDTO;
import com.proj.animore.dto.RboardListReqDTO;
import com.proj.animore.form.BoardForm;
import com.proj.animore.form.Code;
import com.proj.animore.form.LoginMember;
import com.proj.animore.svc.BoardSVC;
import com.proj.animore.svc.RboardSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	private final BoardSVC boardSVC;
	private final RboardSVC rboardSVC;
	
	@ModelAttribute("bcategoryCode")
	public List<Code> bcategory(){
		List<Code> list = new ArrayList<>();
		list.add(new Code("Q","Q&A"));
		list.add(new Code("F","자유게시판"));
		list.add(new Code("M","벼룩시장"));
		list.add(new Code("P","내새끼 보세요"));
		return list;
	}
	
	//게시글 목록출력
	@GetMapping("/{bcategory}")
	public String boardList(@PathVariable String bcategory,
							Model model) {
	     if(bcategory.equals("Q"))   bcategory="Q";
	     if(bcategory.equals("M"))   bcategory="M";
	     if(bcategory.equals("F"))   bcategory="F";
	     if(bcategory.equals("P"))   bcategory="P";
		
	     List<BoardReqDTO> list = boardSVC.list(bcategory);
	     model.addAttribute("boardForm",list);
	     
		return "board/board";
	}
	
	//게시글 조회
	@GetMapping("/post/{bnum}")
	public String post(@PathVariable Integer bnum,
										Model model) {
		
		BoardReqDTO boardReqDTO = boardSVC.findBoardByBnum(bnum);
		model.addAttribute("post",boardReqDTO);
		//게시글 조회시 해당 게시글의 댓글목록도 함께 불러옴.
		List<RboardListReqDTO> replyList = rboardSVC.all(bnum);
		model.addAttribute("reply", replyList);
		

		log.info("replyList:{}",replyList);
		
		return "board/boardDetail";
	}
	
	//게시글 작성화면 출력
	@GetMapping("/add")
	public String addPost(@ModelAttribute BoardForm boardForm,
						HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		if(session==null) {
			return "/member/login";
		}
		return "board/addBoardForm";
	}
	
	//게시글 등록처리
	@PostMapping("/add")
	public String addpost(@Valid @ModelAttribute BoardForm boardForm,
							BoardDTO boardDTO,
							BindingResult bindingResult,
							HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String loginMemberId = loginMember.getId();
		
		boardSVC.addBoard(loginMemberId,boardDTO);
		
		return "redirect:/board/Q";
		
	}
}

