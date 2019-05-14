package com.vladmirk.transkontservice.shipping;

import com.vladmirk.transkontservice.party.Common;
import com.vladmirk.transkontservice.party.PartyName;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import static org.springframework.util.StringUtils.isEmpty;

@Entity
public class Transport extends Common {
  @ManyToOne
  private PartyName truckModel;
  private String truckPlateNumber;
  private String trailerPlateNumber;


  public Transport() {
  }

  public Transport(PartyName truckModel, String truckPlateNumber, String trailerPlateNumber) {
    this.truckModel = truckModel;
    this.truckPlateNumber = truckPlateNumber;
    this.trailerPlateNumber = trailerPlateNumber;
  }

  public PartyName getTruckModel() {
    return truckModel;
  }

  public void setTruckModel(PartyName truckModel) {
    this.truckModel = truckModel;
  }
  public String getTruckPlateNumber() {
    return truckPlateNumber;
  }
  public void setTruckPlateNumber(String truckPlateNumber) {
    this.truckPlateNumber = truckPlateNumber;
  }
  public String getTrailerPlateNumber() {
    return trailerPlateNumber;
  }
  public void setTrailerPlateNumber(String trailerPlateNumber) {
    this.trailerPlateNumber = trailerPlateNumber;
  }

  @Transient
  public String getTruck() {
    StringBuilder sb = new StringBuilder("");
    if (getTruckModel() != null)
      sb.append(getTruckModel().toString());
    if (!isEmpty(getTruckPlateNumber()))
      sb.append(" ").append(getTruckPlateNumber());
    return sb.toString();
  }

  @Transient
  public String getTrailer() {
    return isEmpty(getTrailerPlateNumber()) ? "" : getTrailerPlateNumber();
  }

  @Override
  public String toString() {
    String trailer = isEmpty(getTrailer()) ? "" : " - " + getTrailer();
    return getTruck() + trailer;
  }
}
