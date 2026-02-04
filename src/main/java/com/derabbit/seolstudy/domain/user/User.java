package com.derabbit.seolstudy.domain.user;

import com.derabbit.seolstudy.domain.common.BaseTimeEntity;
import com.derabbit.seolstudy.global.exception.CustomException;
import com.derabbit.seolstudy.global.exception.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id", nullable = false)
    private User mentor;

    @Column(nullable = false, length = 50)
    private String name;

    private String school;
    
    private Long grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(nullable = false)
    private Boolean isAssigned;

    public enum Role {
        MENTOR,
        MENTEE
    }

    public void updateProfile(String name, String school) {

        if (name != null && !name.equals(this.name)) {
            this.name = name;
        }

        if (school != null && !school.equals(this.school)) {
            this.school = school;
        }
    }

    public void updateGrade(Long grade) {

        if (grade == null) return;

        if (grade < 1 || grade > 3) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        if (!grade.equals(this.grade)) {
            this.grade = grade;
        }
    }

    public void assignMentor(User mentor) {
        
        if (this.getRole() != User.Role.MENTEE) {
            throw new CustomException(ErrorCode.INVALID_TARGET_USER);
        }

        this.mentor = mentor;
        this.isAssigned = true;
        
    }
}