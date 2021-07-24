package com.github.gustavomaciel.dev.api.branch.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.validation.BindingResult;

public class ApiErrors extends Exception{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private List<String> errors;
  
  public ApiErrors(BindingResult bindingResult) {
    this.errors = new ArrayList<>();
    bindingResult.getAllErrors().forEach(error -> this.errors.add(error.getDefaultMessage()));
  }
  
  public ApiErrors(BusinessException ex) {
    this.errors = Arrays.asList(ex.getMessage());
  }

  public List<String> getErrors(){
    return errors;
  }
}
