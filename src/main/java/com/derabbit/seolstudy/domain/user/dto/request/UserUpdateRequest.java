package com.derabbit.seolstudy.domain.user.dto.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String password;
    private String name;
    private String school;
    private Long grade; // MENTEE만 필요
}