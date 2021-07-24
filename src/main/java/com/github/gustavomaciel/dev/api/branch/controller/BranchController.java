 package com.github.gustavomaciel.dev.api.branch.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.gustavomaciel.dev.api.branch.dto.BranchDTO;
import com.github.gustavomaciel.dev.api.branch.exceptions.ApiErrors;
import com.github.gustavomaciel.dev.api.branch.exceptions.BusinessException;
import com.github.gustavomaciel.dev.api.branch.model.Branch;
import com.github.gustavomaciel.dev.api.branch.service.BranchService;

@RestController
@RequestMapping("/api/branches")
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
    Branch branch = modelMapper.map(dto, Branch.class);
    branch = service.save(branch); 
    
    return modelMapper.map(branch, BranchDTO.class);
  }
  
  
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrors handleValidationException(MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult(); 
    
    return new ApiErrors(bindingResult);
  }
  
  @ExceptionHandler(BusinessException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrors handleBusinessException(BusinessException  ex) {
    
    return new ApiErrors(ex);
  }
}
 