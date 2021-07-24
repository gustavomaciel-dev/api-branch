package com.github.gustavomaciel.dev.api.branch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.gustavomaciel.dev.api.branch.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long>{

  boolean existsByAddress(String address);

}
