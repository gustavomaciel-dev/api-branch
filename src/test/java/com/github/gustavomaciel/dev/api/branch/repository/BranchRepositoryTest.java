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

import com.github.gustavomaciel.dev.api.branch.model.Branch;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureMockMvc
public class BranchRepositoryTest {

  @Autowired
  TestEntityManager entityManager;
  
  @Autowired
  BranchRepository repository;
  
  @Test
  @DisplayName("Should return true when Branch exists in database")
  public void returnTrueWhenIdExists() {
    //stage
    Branch branch = createBranch();
    branch = entityManager.persist(branch);
    
    //execution
    boolean exists = repository.existsById(branch.getId());
    
    //verify
    assertThat(exists).isTrue();
  }

  private Branch createBranch() {
    Branch branch = Branch.builder()
                                  .address("calle808")
                                  .latitude(1234D)
                                  .longitude(4321D)
                                  .build();
    return branch;
  }
  
  @Test
  @DisplayName("Should return false when Branch doesnt exist in database")
  void returnFalseWhenAddressDoesntExist() {
    //stage
    String address = "calle808";
    
    //execution
    boolean exists = repository.existsByAddress(address);
    
    //verify
    assertThat(exists).isFalse();
  }
  
  @Test
  @DisplayName("Should return true when Branch exists in database")
  void returnTrueWhenAddressExists() {
    //stage
    //stage
    Branch branch = createBranch();
    branch = entityManager.persist(branch);
        
    //execution
    boolean exists = repository.existsByAddress(branch.getAddress());
    
    //verify
    assertThat(exists).isTrue();
  }
  
  @Test
  @DisplayName("Should return true when a Branch with these latitude and longitude was found in database")
  void returnTrueWhenLatitudeLongitudeExists() {
    //stage
    Branch branch = createBranch();
    branch = entityManager.persist(branch);
        
    //execution
    boolean exists = repository.existsByLatitudeAndLongitude(branch.getLatitude(), branch.getLongitude());
    
    //verify
    assertThat(exists).isTrue();
  }
  
  @Test
  @DisplayName("Should return a Branch by Latitude and Longitude it exists in database")
  void returnBranchByLatitudeLongitude() {
    //stage
    Branch branch = createBranch();
    branch = entityManager.persist(branch);
        
    //execution
    Optional<Branch> branchFound = repository.findByLatitudeAndLongitude(branch.getLatitude(), branch.getLongitude());
    
    //verify
    assertThat(branchFound.isPresent()).isTrue();
  }
  
  @Test
  @DisplayName("Should return a Branch by Id")
  void findByIdTest() {
    //stage
    Branch branch = createBranch();
    entityManager.persist(branch);
    
    //execution
    Optional<Branch> branchFound = repository.findById(branch.getId());
    
    //verify
    assertThat(branchFound.isPresent()).isTrue();
  }
  
}
