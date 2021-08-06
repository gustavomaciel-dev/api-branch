package com.github.gustavomaciel.dev.api.branch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class City {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  @Id
  private Long id;
  
  @Column
  private String name;
  
  @Column
  private String postalCode;
  
//  @OneToOne(mappedBy = "city")
//  @ToStringExclude
//  private Address address;
  
  @ManyToOne
  @JoinColumn(name = "province_id", referencedColumnName = "id")
  private Province province;

  
  
}
