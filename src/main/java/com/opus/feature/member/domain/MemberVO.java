package com.opus.feature.member.domain;

import com.opus.utils.SecurityUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberVO {

	private Integer memberId;

	private String username;

	private String password;

	private String nickname;

	private String email;

	public static MemberVO fromRegistrationDTO(MemberRegisterRequestDTO memberRegisterRequestDTO) {

		return MemberVO.builder()
			.username(memberRegisterRequestDTO.getUsername())
			.password(memberRegisterRequestDTO.getPassword())
			.nickname(memberRegisterRequestDTO.getNickname())
			.email(memberRegisterRequestDTO.getEmail())
			.build();
	}

	public static MemberVO of(MemberEditRequestDTO memberEditRequestDTO) {

		return MemberVO.builder()
			.memberId(SecurityUtil.getCurrentUserId())
			.password(memberEditRequestDTO.getPassword())
			.nickname(memberEditRequestDTO.getNickname())
			.email(memberEditRequestDTO.getEmail())
			.build();
	}
}
