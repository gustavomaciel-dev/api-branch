package com.github.gustavomaciel.dev.api.branch.service;

import static org.assertj.core.api.Assertions.assertThat;

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
    Mockito.when(repository.existsByAddress(Mockito.anyString())).thenReturn(false);
    Mockito.when(repository.save(branch)).thenReturn(
                                                      Branch.builder().id(101L)
                                                      .address("calle808")
                                                      .latitude(1234D)
                                                      .longitude(4321D)
                                                      .build()
                                                    );
    //execution
    Branch branchSaved = service.save(branch);
    
    //verify
    assertThat(branchSaved.getId()).isNotNull();
    assertThat(branchSaved.getAddress()).isEqualTo("calle808");
    assertThat(branchSaved.getLatitude()).isEqualTo(1234);
    assertThat(branchSaved.getLongitude()).isEqualTo(4321);
    
  }

  private Branch createValidBranch() {
    Branch branch = Branch.builder().address("calle808").latitude(1234D).longitude(4321D).build();
    return branch;
  }
  
  @Test
  @DisplayName("Should return an exception when try to save a branch with address already exists")
  public void shouldNotSaveABranchAddressAlreadyExists() {
    String errorMessage = "Ya existe Sucursal con la direccion indicada";

    //stage
    Branch branch = createValidBranch();
    Mockito.when(repository.existsByAddress(Mockito.anyString())).thenReturn(true);
    
    //execution
    Throwable excpetion = Assertions.catchThrowable( () -> service.save(branch));
    
    //verify
    assertThat(excpetion)
    .isInstanceOf(BusinessException.class)
    .hasMessage(errorMessage);
    
    Mockito.verify(repository, Mockito.never()).save(branch);
    
  }
}
