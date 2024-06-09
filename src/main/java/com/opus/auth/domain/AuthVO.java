package com.opus.auth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthVO {

        private Integer memberId;

        private String userName;

        private String password;

        private String nickname;

        private String email;

        public static AuthVO of(LoginDTO loginDTO) {
                return new AuthVO(null, loginDTO.getUserName(), loginDTO.getPassword(), null, null);
        }
}