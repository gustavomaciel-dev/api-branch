package com.github.gustavomaciel.dev.api.branch.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.gustavomaciel.dev.api.branch.model.Branch;
import com.github.gustavomaciel.dev.api.branch.repository.BranchRepository;
import com.github.gustavomaciel.dev.api.branch.service.BranchService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class BranchServiceImpl implements BranchService {

  @PersistenceContext
  private EntityManager entityManager;
  private BranchRepository repository;
  private static final String ALREADY_EXISTS_MESSAGE = "Ya existe una Sucursal con la direccion indicada";
  
  
  public BranchServiceImpl(BranchRepository repository) {
    this.repository = repository;
  }

  @Override
  public Branch save(Branch branch)  {
    log.debug("save service method was invoked");
//    if (repository.existsByAddress(branch.getAddress())) {
//      log.error("the address already exists!");
//      throw new BusinessException(ALREADY_EXISTS_MESSAGE);
//    }
    entityManager.persist(branch.getAddress());
    entityManager.flush();
    log.debug("invoking save repository method");
    return repository.save(branch);
  }


  @Override
  public Optional<Branch> getById(Long id) {
    log.debug("getById service method was invoked");
    log.debug("invoking repository method findById");
    return repository.findById(id);
  }

  @Override
  public List<Branch> getAll() {
    log.debug("getAll service method was invoked");
    log.debug("invoking repository method findAll");

    return repository.findAll();
  }
  
}
