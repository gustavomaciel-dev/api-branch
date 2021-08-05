package com.github.gustavomaciel.dev.api.branch.model;

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
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Slf4j
public class Branch {
  
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  @Id
  public Long id;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id", referencedColumnName = "id")
  @ToStringExclude private Address address = new Address();

  /**
   * 
   * @param lat
   * @param longit
   * @return Double as distance in meters
   */
  public Double distance(double lat, double longit) {
    double nearDistance = 0;
    log.debug("distance method was invoked with params, latitude: {}, longitude: {}", lat, longit);
    double theta = this.address.getLongitude() - longit;
    double distance = Math.sin(decimalToRadians(this.address.getLatitude())) * Math.sin(decimalToRadians(lat)) + Math.cos(decimalToRadians(this.address.getLatitude())) * Math.cos(decimalToRadians(lat)) * Math.cos(decimalToRadians(theta));
    distance = Math.acos(distance);
    distance = radiansToDecimal(distance);
    distance = distance * 60 * 1.1515;
    nearDistance = distance * 1.609344;
    log.debug("distance for branchID {} is {}", this.id, nearDistance);
    return nearDistance;
  }

  private static double decimalToRadians(double deg) {
    return (deg * Math.PI / 180.0);
  }

  private static double radiansToDecimal(double rad) {
    return (rad * 180.0 / Math.PI);
  }
}
