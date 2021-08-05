package com.github.gustavomaciel.dev.api.branch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.gustavomaciel.dev.api.branch.model.Address;
import com.github.gustavomaciel.dev.api.branch.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long>{

  boolean existsByAddress(Address address);

  Optional<Branch> findByAddress(Address address);

}
