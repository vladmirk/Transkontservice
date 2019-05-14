package com.vladmirk.transkontservice.shipping;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransportRepository extends CrudRepository<Transport, Long> {

  //ByNameContainingOrSecondNameContainingOrSurnameContainingOrPassportContainingAllIgnoreCase
  List<Transport> findTransportByTruckPlateNumberContainingOrTrailerPlateNumberContainingAllIgnoreCase(String truckPlate, String trailerPlate);
}
