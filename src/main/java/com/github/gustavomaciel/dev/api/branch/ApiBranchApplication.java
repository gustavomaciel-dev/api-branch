package com.github.gustavomaciel.dev.api.branch;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiBranchApplication {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
  
  public static void main(String[] args) {
	SpringApplication.run(ApiBranchApplication.class, args);
  }

}
