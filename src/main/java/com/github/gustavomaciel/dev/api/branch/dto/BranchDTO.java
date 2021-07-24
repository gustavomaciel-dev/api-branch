package com.github.gustavomaciel.dev.api.branch.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO {
  
  private Long id;

  @NotNull
  private String address;
  
  @NotNull
  private Double latitude;
  
  @NotNull
  private Double longitude;

  
}
