package com.github.gustavomaciel.dev.api.branch.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

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
@WebMvcTest(controllers = BranchController.class)
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
    Branch savedBranch = Branch.builder().id(1L).address("mendoza").latitude(1234D).longitude(4321D).build();
    
    BDDMockito.given(service.save(Mockito.any(Branch.class))).willReturn(savedBranch); 
    String json = new ObjectMapper().writeValueAsString(dto);
        
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    .post(BRANCH_API)
    .contentType(MediaType.APPLICATION_JSON)
    .accept(MediaType.APPLICATION_JSON)
    .content(json);
    
    mvc.perform(request)
    .andExpect(status().isCreated())
    .andExpect(jsonPath("id").value(1))
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
  @DisplayName("Should return a validation error when a branch already exists")
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
  
  @Test
  @DisplayName("Should return a branch")
  public void getBranchTest() throws Exception {
    
    Long id = 1L;
    Branch branch = Branch.builder()
                                  .id(1L)
                                   .address(createBranch().getAddress())
                                   .latitude(createBranch().getLatitude())
                                   .longitude(createBranch().getLongitude())
                                   .build();
    
    BDDMockito.given(service.getById(id)).willReturn(Optional.of(branch));
    
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                                                .get(BRANCH_API.concat("/"+id))
                                                                .accept(MediaType.APPLICATION_JSON);
    
    mvc
      .perform(request)
      .andExpect(status().isOk())
      .andExpect(jsonPath("id").value(1))
      .andExpect(jsonPath("address").value(createBranch().getAddress()))
      .andExpect(jsonPath("latitude").value(createBranch().getLatitude()))
      .andExpect(jsonPath("longitude").value(createBranch().getLongitude()));    
      
  }
  
  @Test
  @DisplayName("Should return resource not found when a branch doesnts exists")
  public void branchNotFoundTest() throws Exception{
    BDDMockito.given(service.getById(Mockito.anyLong())).willReturn(Optional.empty());
    
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                                                .get(BRANCH_API.concat("/"+1))
                                                                .accept(MediaType.APPLICATION_JSON);

    mvc
    .perform(request)
    .andExpect(status().isNotFound());

  }
  
  
  @Test
  @DisplayName("Should get nearest Branch")
  public void findAllBranchTest() throws Exception{
    
    Branch branchRequest = Branch.builder()
        .id(1L)
        .address("calle808")
        .latitude(1235D)
        .longitude(4322D)
        .build();
    
    Branch nearestBranch = Branch.builder()
                          .id(1L)
                          .address("calle808")
                          .latitude(1234D)
                          .longitude(4321D)
                          .build();
    
    Branch branch2 = Branch.builder()
                        .id(2L)
                        .address("calle809")
                        .latitude(5234D)
                        .longitude(7321D)
                        .build();
    Branch branch3 = Branch.builder()
                        .id(3L)
                        .address("calle810")
                        .latitude(5234D)
                        .longitude(6321D)
                        .build();

    BDDMockito.given( service.getAll())
                    .willReturn( Arrays.asList(branch2, nearestBranch, branch3) );
    
    String queryString = String.format("/nearest?latitude=%s&longitude=%s&page=0&size=0", 
        branchRequest.getLatitude(),
        branchRequest.getLongitude());
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                                          .get(BRANCH_API.concat(queryString))
                                                          .accept(MediaType.APPLICATION_JSON);
    
    mvc
      .perform(request)
      .andExpect( status().isOk() )
      .andExpect(jsonPath("id").value(nearestBranch.getId()))
      .andExpect(jsonPath("address").value(nearestBranch.getAddress()))
      .andExpect(jsonPath("latitude").value(nearestBranch.getLatitude()))
      .andExpect(jsonPath("longitude").value(nearestBranch.getLongitude()));
  }
  
  private BranchDTO createBranch() {
    BranchDTO dto = BranchDTO.builder().address("mendoza").latitude(1234D).longitude(4321D).build();
    return dto;
  }
  
}
