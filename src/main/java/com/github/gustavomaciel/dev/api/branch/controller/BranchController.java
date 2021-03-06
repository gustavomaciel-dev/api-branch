 package com.github.gustavomaciel.dev.api.branch.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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
import com.github.gustavomaciel.dev.api.branch.model.Address;
import com.github.gustavomaciel.dev.api.branch.model.Branch;
import com.github.gustavomaciel.dev.api.branch.service.BranchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/branches")
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
    Address address = modelMapper.map(dto, Address.class);
    Branch branch = Branch.builder().address(address).build();
    branch = service.save(branch); 
    log.info("new branch was created");
    BranchDTO branchDTO = modelMapper.map(branch.getAddress(), BranchDTO.class);
    branchDTO.setBranchId(branch.getId());
    return branchDTO;
  }
  
  @GetMapping("{id}")
  public BranchDTO get(@PathVariable Long id) {
    log.debug("get endpoint was invoked with ID param: {}", id);
    Optional<Branch> branchDTO = service.getById(id);
    BranchDTO dto = branchDTO.map(branch -> modelMapper.map(branch.getAddress(), BranchDTO.class))
        .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND));
        dto.setBranchId(branchDTO.isPresent() ? branchDTO.get().getId() : id);
    return dto;
  }
  
  @GetMapping("/nearest")
  public ResponseEntity<BranchDTO> findNearest(BranchDTO branchDTO){
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
    log.debug("calculaing the nearest branch...");
    branchList.sort( (Branch b1, Branch b2) -> b1.distance(branchDTO.getLatitude(), branchDTO.getLongitude())
                                              .compareTo(b2.distance(branchDTO.getLatitude(), branchDTO.getLongitude())) );
    log.debug("return nearest branch: {}", branchList.get(0).toString());
    Address address = modelMapper.map(branchList.get(0).getAddress(), Address.class);
    BranchDTO responseDTO = modelMapper.map(address, BranchDTO.class);
    responseDTO.setBranchId(branchList.get(0).getId());
    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    
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
 