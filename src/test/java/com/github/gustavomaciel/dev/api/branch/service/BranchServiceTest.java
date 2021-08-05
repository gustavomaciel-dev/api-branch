package com.github.gustavomaciel.dev.api.branch.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.gustavomaciel.dev.api.branch.exceptions.BusinessException;
import com.github.gustavomaciel.dev.api.branch.model.Address;
import com.github.gustavomaciel.dev.api.branch.model.Branch;
import com.github.gustavomaciel.dev.api.branch.repository.BranchRepository;
import com.github.gustavomaciel.dev.api.branch.service.impl.BranchServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BranchServiceTest {

  BranchService service;
  
  @MockBean
  BranchRepository repository;
  
  
  @BeforeEach
  public void setUp() {
    this.service = new BranchServiceImpl(repository);
  }
  
  @Test
  @DisplayName("Should saves a Branch")
  public void saveBranchTest() {

    //stage
    Branch branch = createValidBranch();
    Mockito.when(repository.existsByAddress(Mockito.any())).thenReturn(false);
    Mockito.when(repository.save(branch)).thenReturn(branch);
    //execution
    Branch branchSaved = service.save(branch);
    
    //verify
    assertThat(branchSaved.getId()).isNotNull();
    assertThat(branchSaved.getAddress().getStreet()).isEqualTo("calle808");
    assertThat(branchSaved.getAddress().getLatitude()).isEqualTo(1234);
    assertThat(branchSaved.getAddress().getLongitude()).isEqualTo(4321);
    
  }

  private Branch createValidBranch() {
    Address address = Address.builder().street("calle808").latitude(1234D).longitude(4321D).build();
    return Branch.builder()
                        .id(1L)
                        .address(address).build();
    
  }
  
  @Test
  @DisplayName("Should return an exception when try to save a branch with address already exists")
  public void shouldNotSaveABranchAddressAlreadyExists() {
    String errorMessage = "Ya existe una Sucursal con la direccion indicada";

    //stage
    Branch branch = createValidBranch();
    Mockito.when(repository.existsByAddress(Mockito.any())).thenReturn(true);
    
    //execution
    Throwable excpetion = Assertions.catchThrowable( () -> service.save(branch));
    
    //verify
    assertThat(excpetion)
    .isInstanceOf(BusinessException.class)
    .hasMessage(errorMessage);
    
    Mockito.verify(repository, Mockito.never()).save(branch);
    
  }
  
  @Test
  @DisplayName("Should get a branch by ID")
  public void getBranchByIdTest() {
    
    Long id = 101L;
    Branch branch = createValidBranch();
    branch.setId(id);
    Mockito.when(repository.findById(id)).thenReturn(Optional.of(branch));
    
    
    Optional<Branch> branchFound = service.getById(id);
    
    
    assertThat( branchFound.isPresent()).isTrue();
    assertThat( branchFound.get().getId()).isEqualTo(id);
    assertThat( branchFound.get().getAddress()).isEqualTo(branch.getAddress());
    assertThat( branchFound.get().getAddress().getLatitude()).isEqualTo(branch.getAddress().getLatitude());
    assertThat( branchFound.get().getAddress().getLongitude()).isEqualTo(branch.getAddress().getLongitude());
    
  }
  
  @Test
  @DisplayName("Should return empty when branch doesnt exist")
  public void branchNotFoundByIdTetst() {
    
    Long id = 101L;
    Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
    
    
    Optional<Branch> branchFound = service.getById(id);
    
    
    assertThat( branchFound.isPresent()).isFalse();
    
  }
  
  @Test
  @DisplayName("Should filter branch by properties")
  public void findBranchTest() {
    
    Branch branch = createValidBranch();
    Branch branch2 = createValidBranch();

    Mockito.when(repository.findAll())
            .thenReturn(Arrays.asList(branch,branch2));
    
    List<Branch> result = service.getAll();
    
    assertThat(result.size()).isEqualTo(2);
  }
}















































