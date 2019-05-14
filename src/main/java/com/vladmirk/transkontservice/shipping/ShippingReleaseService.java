package com.vladmirk.transkontservice.shipping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingReleaseService {
  private ShippingReleaseRepository shippingReleaseRepository;
  private ShippingOrderRepository orderRepository;
  private CarrierInfoRepository carrierInfoRepository;

  @Autowired
  public ShippingReleaseService(final ShippingReleaseRepository shippingReleaseRepository, final ShippingOrderRepository orderRepository,
      final CarrierInfoRepository carrierInfoRepository) {
    this.shippingReleaseRepository = shippingReleaseRepository;
    this.orderRepository = orderRepository;
    this.carrierInfoRepository = carrierInfoRepository;
  }

  public List<ShippingRelease> findShippingReleases() {
    return (List<ShippingRelease>) shippingReleaseRepository.findAll();
  }

  public Optional<ShippingRelease> findById(Long id) {
    return shippingReleaseRepository.findById(id);
  }

  public ShippingRelease save(ShippingRelease sr) {
    return shippingReleaseRepository.save(sr);
  }

  public Optional<ShippingOrder> findOrderById(Long id) {
    return orderRepository.findById(id);
  }

  public ShippingOrder saveOrder(ShippingOrder order) {
    return orderRepository.save(order);
  }


  public CarrierInfo createNewCarrierInfo() {
    return carrierInfoRepository.save(new CarrierInfo());
  }

  public Optional<CarrierInfo> findCarrierInfoById(Long id) {
    return carrierInfoRepository.findById(id);
  }

  public CarrierInfo saveCarrierInfo(CarrierInfo carrierInfo) {
    return carrierInfoRepository.save(carrierInfo);
  }


}
