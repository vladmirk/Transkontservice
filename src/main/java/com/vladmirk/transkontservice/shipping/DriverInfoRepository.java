package com.vladmirk.transkontservice.shipping;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DriverInfoRepository extends CrudRepository<DriverInfo, Long> {

  List<DriverInfo> findDriverInfoByNameContainingOrSecondNameContainingOrSurnameContainingOrPassportContainingAllIgnoreCase(String name,
      String secondName, String Surname, String passport);


}
