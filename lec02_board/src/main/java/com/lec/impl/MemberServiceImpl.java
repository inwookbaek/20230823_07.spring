package com.lec.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lec.domain.Member;
import com.lec.persistence.MemberRepository;
import com.lec.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	// MemberDAO memberDAO = MemberDAO.getInstance()
	@Autowired
	private MemberRepository memberRepo;  
	
	@Override
	public long getTotalRowCount(Member member) {
		return memberRepo.count();
	}

	@Override
	public Member getMember(Member member) {
		Optional<Member> findMember = memberRepo.findById(member.getId());
		
		if(findMember.isPresent()) {
			return findMember.get();			
		} else {
			return null;
		}
	}

	@Override
	public Page<Member> getMemberList(Pageable pageable, String searchType, String searchWord) {
		if(searchType.equalsIgnoreCase("id")) {
			return memberRepo.findByIdContaining(searchWord, pageable);
		} else {
			return memberRepo.findByNameContaining(searchWord, pageable);			
		}
	}

	@Override
	public void insertMember(Member member) {
		memberRepo.save(member);  // insert into member ...		
	}
	
	@Override
	public void deleteMember(Member member) {
		memberRepo.deleteById(member.getId());	
	}

	@Override
	public void updateMember(Member member) {
		memberRepo.save(member); // update member...
	}

}
