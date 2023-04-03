package com.cos.mixin.dto.user;

import com.cos.mixin.domain.user.User;
import com.cos.mixin.until.CustomDateUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserRespDto {
	@Setter
    @Getter
    public static class LoginRespDto {
        private Long id;
        private String username;
        private String createdAt;

        public LoginRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUserName();
            this.createdAt = CustomDateUtil.toStringFormat(user.getCreatedAt());
        }
    }

    @ToString
    @Setter
    @Getter
    public static class JoinRespDto {
        private Long id;
        private String username;


        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUserName();
        }
    }
}
