package com.github.gustavomaciel.dev.api.branch.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Address implements Serializable{

  private static final long serialVersionUID = -4218219956799787959L;
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  @Id
  private Long id;
  @Column
  private String street;
  @Column
  private Double latitude;
  @Column
  private Double longitude;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "city_id", referencedColumnName = "id")
  @ToStringExclude
  private City city;
  
  @OneToOne(mappedBy = "address")
  private Branch branch;

}