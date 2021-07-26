package com.github.gustavomaciel.dev.api.branch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Slf4j
public class Branch {
  
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column
  private String address;
  
  @Column
  private Double latitude;
  
  @Column
  private Double longitude;

  /**
   * 
   * @param lat
   * @param longit
   * @return Double as distance in meters
   */
  public Double distance(double lat, double longit) {
    log.debug("distance method was invoked with params, latitude: {}, longitude: {}", lat, longit);
    double theta = this.longitude - longit;
    double distance = Math.sin(decimalToRadians(this.latitude)) * Math.sin(decimalToRadians(lat)) + Math.cos(decimalToRadians(this.latitude)) * Math.cos(decimalToRadians(lat)) * Math.cos(decimalToRadians(theta));
    distance = Math.acos(distance);
    distance = radiansToDecimal(distance);
    distance = distance * 60 * 1.1515;
    return distance * 1.609344;
  }

  private static double decimalToRadians(double deg) {
    return (deg * Math.PI / 180.0);
  }

  private static double radiansToDecimal(double rad) {
    return (rad * 180.0 / Math.PI);
  }
}
