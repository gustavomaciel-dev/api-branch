package com.github.gustavomaciel.dev.api.branch.service.impl;

import org.springframework.stereotype.Service;

import com.github.gustavomaciel.dev.api.branch.exceptions.BusinessException;
import com.github.gustavomaciel.dev.api.branch.model.Branch;
import com.github.gustavomaciel.dev.api.branch.repository.BranchRepository;
import com.github.gustavomaciel.dev.api.branch.service.BranchService;

@Service
public class BranchServiceImpl implements BranchService {

  private BranchRepository repository;
  private static final String ERROR_MESSAGE = "Ya existe Sucursal con la direccion indicada";
  
  
  public BranchServiceImpl(BranchRepository repository) {
    this.repository = repository;
  }

  @Override
  public Branch save(Branch branch)  {
    if (repository.existsByAddress(branch.getAddress())) {
      throw new BusinessException(ERROR_MESSAGE);
    }
    return repository.save(branch);
  }

}
