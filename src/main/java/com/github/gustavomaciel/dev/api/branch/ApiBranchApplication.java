package com.github.gustavomaciel.dev.api.branch;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Branches API", version = "1.0", description = "Branches information"))

public class ApiBranchApplication {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
  
  public static void main(String[] args) {
	SpringApplication.run(ApiBranchApplication.class, args);
  }

}
