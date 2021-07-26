package com.github.gustavomaciel.dev.api.branch.service;

import java.util.List;
import java.util.Optional;

import com.github.gustavomaciel.dev.api.branch.model.Branch;

public interface BranchService {
  
  Optional<Branch> getById(Long id);

  Branch save(Branch any);

  List<Branch> getAll();

}
