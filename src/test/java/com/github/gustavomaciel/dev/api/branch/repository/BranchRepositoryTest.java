package com.github.gustavomaciel.dev.api.branch.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.gustavomaciel.dev.api.branch.model.Address;
import com.github.gustavomaciel.dev.api.branch.model.Branch;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureMockMvc
class BranchRepositoryTest {

  @Autowired
  TestEntityManager entityManager;
  
  @Autowired
  BranchRepository repository;
  
  @Test
  @DisplayName("Should return true when Branch exists in database")
  public void returnTrueWhenIdExists() {
    //stage
    Branch branch = createBranch(1234D);
    branch = entityManager.persist(branch);
    
    //execution
    boolean exists = repository.existsById(branch.getId());
    
    //verify
    assertThat(exists).isTrue();
  }

  private Branch createBranch(Double latitud) {
    Address address = Address.builder()
                                    .street("calle808")
                                    .latitude(latitud)
                                    .longitude(4321D)
                                    .build();
    Branch branch = Branch.builder().address(address)
                                    .build();
    return branch;
  }
  
  
  @Test
  @DisplayName("Should return true when Branch exists in database")
  void returnTrueWhenAddressExists() {
    //stage
    //stage
    Branch branch = createBranch(4567D);
    branch = entityManager.persist(branch);
        
    //execution
    boolean exists = repository.existsByAddress(branch.getAddress());
    
    //verify
    assertThat(exists).isTrue();
  }
  
  @Test
  @DisplayName("Should return a Branch by Latitude and Longitude it exists in database")
  void returnBranchByLatitudeLongitude() {
    //stage
    Branch branch = createBranch(9876D);
    branch = entityManager.persist(branch);
        
    //execution
    Optional<Branch> branchFound = repository.findByAddress(branch.getAddress());
    
    //verify
    assertThat(branchFound.isPresent()).isTrue();
  }
  
  @Test
  @DisplayName("Should return a Branch by Id")
  void findByIdTest() {
    //stage
    Branch branch = createBranch(6543D);
    entityManager.persist(branch);
    
    //execution
    Optional<Branch> branchFound = repository.findById(branch.getId());
    
    //verify
    assertThat(branchFound.isPresent()).isTrue();
  }
  
}
