 package com.github.gustavomaciel.dev.api.branch.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.github.gustavomaciel.dev.api.branch.dto.BranchDTO;
import com.github.gustavomaciel.dev.api.branch.exceptions.ApiErrors;
import com.github.gustavomaciel.dev.api.branch.exceptions.BusinessException;
import com.github.gustavomaciel.dev.api.branch.model.Branch;
import com.github.gustavomaciel.dev.api.branch.service.BranchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/branches")
@Slf4j
public class BranchController {
  
  private BranchService service;
  private ModelMapper modelMapper;
  
  
  public BranchController(BranchService service, ModelMapper modelMapper) {
    this.service = service;
    this.modelMapper = modelMapper;
  }


  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BranchDTO create(@RequestBody @Valid BranchDTO dto) {
    log.debug("create endpoint was invoked");
    Branch branch = modelMapper.map(dto, Branch.class);
    branch = service.save(branch); 
    log.info("new branch was created");
    return modelMapper.map(branch, BranchDTO.class);
  }
  
  @GetMapping("{id}")
  public BranchDTO get(@PathVariable Long id) {
    log.debug("get endpoint was invoked with ID param: {}", id);
    return service
                .getById(id)
                .map( branch -> modelMapper.map(branch, BranchDTO.class) )
                .orElseThrow( () ->  new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
  
  @GetMapping("/nearest")
  public ResponseEntity<BranchDTO> findNearest(BranchDTO branchDTO, Pageable pageRequest){
    log.debug("findNearest endpoint was invoked");
    if (null == branchDTO.getLatitude() || null == branchDTO.getLongitude()) {
      log.error("bad request, latidude: {}, longitude: {}", branchDTO.getLatitude(), branchDTO.getLongitude());
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    List<Branch> branchList = service.getAll();
    if (branchList.isEmpty()) {
      log.debug("call to service.getAll() without results");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    assert branchList.isEmpty(): "La lista no puede estar vacia!";  
    log.debug("calculaing the nearest branch...");
    branchList.sort( (Branch b1, Branch b2) -> b1.distance(branchDTO.getLatitude(), branchDTO.getLongitude())
                                              .compareTo(b2.distance(branchDTO.getLatitude(), branchDTO.getLongitude())) );
    log.debug("return nearest branch: {}", branchList.get(0).toString());
    return new ResponseEntity<>(modelMapper.map(branchList.get(0), BranchDTO.class), HttpStatus.OK);
    
  }
  
  
  
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrors handleValidationException(MethodArgumentNotValidException ex) {
    log.error("an exception was throwed: {}", ex.getMessage());
    BindingResult bindingResult = ex.getBindingResult(); 
    return new ApiErrors(bindingResult);
  }
  
  @ExceptionHandler(BusinessException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrors handleBusinessException(BusinessException  ex) {
    log.error("an exception was throwed: {}", ex.getMessage());
    return new ApiErrors(ex);
  }
  
}
 