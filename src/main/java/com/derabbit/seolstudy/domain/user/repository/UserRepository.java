package com.derabbit.seolstudy.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derabbit.seolstudy.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // 로그인용
    Optional<User> findByEmail(String email);
}
