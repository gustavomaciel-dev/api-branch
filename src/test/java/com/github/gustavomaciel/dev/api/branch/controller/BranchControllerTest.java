package com.github.gustavomaciel.dev.api.branch.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavomaciel.dev.api.branch.dto.BranchDTO;
import com.github.gustavomaciel.dev.api.branch.exceptions.BusinessException;
import com.github.gustavomaciel.dev.api.branch.model.Branch;
import com.github.gustavomaciel.dev.api.branch.service.BranchService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BranchControllerTest {

  static String BRANCH_API = "/api/branches";
  
  @Autowired
  MockMvc mvc;
  
  @MockBean
  BranchService service;
  
  @Test
  @DisplayName("Generates a branch with a successful result")
  public void createBranchTest() throws Exception {
    
    BranchDTO dto = createBranch();
    Branch savedBranch = Branch.builder().id(101L).address("mendoza").latitude(123D).longitude(321D).build();
    
    BDDMockito.given(service.save(Mockito.any(Branch.class))).willReturn(savedBranch); 
    String json = new ObjectMapper().writeValueAsString(dto);
        
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    .post(BRANCH_API)
    .contentType(MediaType.APPLICATION_JSON)
    .accept(MediaType.APPLICATION_JSON)
    .content(json);
    
    mvc.perform(request)
    .andExpect(status().isCreated())
    .andExpect(jsonPath("id").value(101))
    .andExpect(jsonPath("address").value(dto.getAddress()))
    .andExpect(jsonPath("latitude").value(dto.getLatitude()))
    .andExpect(jsonPath("longitude").value(dto.getLongitude()));    
  }


  @Test
  @DisplayName("should return a validation error when there is not enough data")
  public void createInvalidBranchTest() throws Exception {
    
    String json = new ObjectMapper().writeValueAsString(new BranchDTO());
    
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
        .post(BRANCH_API)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(json);
        
    mvc.perform(request)
        .andExpect(status().isBadRequest() )
        .andExpect(jsonPath("errors", hasSize(3))) ;
  }
  
  @Test
  @DisplayName("Should return a validation error when a branch with latitud and longitud already exists")
  public void createBranchWithAddressDuplicated() throws Exception {
    String errorMessage = "Ya existe Sucursal con la direccion indicada";
    String json = new ObjectMapper().writeValueAsString(createBranch());
    
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
        .post(BRANCH_API)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(json);
     BDDMockito.given(service.save(Mockito.any(Branch.class))).willThrow(new BusinessException(errorMessage));
    
     mvc.perform(request)
       .andExpect(status().isBadRequest())
       .andExpect(jsonPath("errors", hasSize(1)))
       .andExpect(jsonPath("errors[0]").value(errorMessage));
  }
  
  
  
  private BranchDTO createBranch() {
    BranchDTO dto = BranchDTO.builder().address("mendoza").latitude(123D).longitude(321D).build();
    return dto;
  }
  
}
