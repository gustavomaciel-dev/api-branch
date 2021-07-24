package com.github.gustavomaciel.dev.api.branch.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
  public void returnTrueWhenAddressExists() {
    //stage
    String address = "calle808";
    Branch branch = Branch.builder().address(address).latitude(1234D).longitude(4321D).build();
    entityManager.persist(branch);
    
    //execution
    boolean exists = repository.existsByAddress(address);
    
    //verify
    assertThat(exists).isTrue();
  }
  
  @Test
  @DisplayName("Should return false when Branch doesnt exist in database")
  public void returnTrueWhenAddressDoesntExist() {
    //stage
    String address = "calle808";
    
    //execution
    boolean exists = repository.existsByAddress(address);
    
    //verify
    assertThat(exists).isFalse();
  }
}
