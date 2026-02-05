package com.derabbit.seolstudy.domain.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derabbit.seolstudy.domain.planner.Planner;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
}
