package com.lec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.lec.domain.Member;
import com.lec.service.MemberService;


@Controller
@SessionAttributes("member")  // sess.setAttribute("member", member)을 자동처리
//@RequestMapping("member")
public class LoginController {

	// 자동주입 
	// MemberService memberService = MemberService.getInstance();
	@Autowired 
	private MemberService memberService;
	

	@GetMapping("/login")
	public String loginView() {
		return "login/login";
	}
	
	@PostMapping("/login")
	public String login(Member member, Model model) {
		
		Member findMember = memberService.getMember(member);

		if(findMember != null && findMember.getPassword().equals(member.getPassword())) {
			model.addAttribute("member", findMember); // sesstion과 request영역에 저장 즉, request.setAttribute("member" member); 
			return "member/getMemberInfo";
		} else {
			return "redirect:login";			
		}	
	}
	
	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		status.setComplete();    // HttpSession.invalidate()동일기능
		return "redirect:login";
	}
}
















