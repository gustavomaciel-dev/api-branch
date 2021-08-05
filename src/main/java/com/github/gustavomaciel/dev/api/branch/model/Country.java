package com.github.gustavomaciel.dev.api.branch.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Country {
  
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  @Id
  private Long id;
  @Column
  private String name;
  @OneToMany(mappedBy="country")
  private Set<Province> provinces;

}
