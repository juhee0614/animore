package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.BusinessDAO;
import com.proj.animore.dao.MemberDAO;
import com.proj.animore.dto.BusinessDTO;
import com.proj.animore.dto.MemberDTO;
import com.proj.animore.dto.ProfessionDTO;
import com.proj.animore.form.FindIdForm;
import com.proj.animore.form.FindPwForm;
import com.proj.animore.form.ChangePwForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC {
	
	private final MemberDAO memberDAO;
	private final BusinessDAO businessDAO;
	
	//일반회원가입
	@Override
	public void joinMember(MemberDTO memberDTO) {
		memberDAO.joinMember(memberDTO);
	}
	
	//사업가회원 가입
	@Override
	public void joinMember(MemberDTO memberDTO, BusinessDTO businessDTO) {
		memberDAO.joinMember(memberDTO);
		businessDAO.joinBusi(businessDTO);
	
	}
	//TODO 전문가 회원가입
	@Override
	public void joinMember(MemberDTO memberDTO, ProfessionDTO professionDTO) {
		// TODO Auto-generated method stub
		
	}
	
	//아이디 중복확인
	@Override
	public boolean isExistId(String id) {
		return memberDAO.isExistId(id);
	}
	
	//닉네임 중복확인
	@Override
	public boolean isExistNickname(String nickname) {
		return memberDAO.isExistNickname(nickname);
	}
	
	//회원찾기 id
	@Override
	public MemberDTO findMemberById(String id) {
		return memberDAO.findMemberById(id);
	}

	//회원정보 수정
	@Override
	public MemberDTO modifyMember(String id, MemberDTO memberDTO) {
		memberDAO.modifyMember(id, memberDTO);
		return memberDAO.findMemberById(id);
	}

	//회원탈퇴
	@Override
	public void deleteMember(String id) {
		memberDAO.deleteMember(id);
	}

	//회원목록 전체조회
	@Override
	public List<MemberDTO> list() {
		// TODO Auto-generated method stub
		return null;
	}

	//로그인시 멤버확인
	@Override
	public MemberDTO findByIdPw(String id, String pw) {
		
		return memberDAO.findByIdPw(id, pw);
	}

	//아이디 찾기
	@Override
	public List<String> findId(FindIdForm findIdForm) {
		return memberDAO.findId(findIdForm);
	}

	//비밀번호 찾기
	@Override
	public ChangePwForm findPw(FindPwForm findPwForm) {
		return memberDAO.findPw(findPwForm);
	}

	//비밀번호 변경
	@Override
	public int changePW(ChangePwForm changePWForm) {
		return memberDAO.changePW(changePWForm);
	}
}
