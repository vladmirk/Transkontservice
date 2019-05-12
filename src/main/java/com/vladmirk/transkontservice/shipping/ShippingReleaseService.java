package com.vladmirk.transkontservice.shipping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ShippingReleaseService {
  private ShippingReleaseRepository shippingReleaseRepository;
  private ShippingOrderRepository orderRepository;

  enum Category {
    LOAD
  }
  private static Map<Category, List<String>> hash = new HashMap();
  static {
    for (Category c : Category.values()) {

    }
  }

  @Autowired
  public ShippingReleaseService(final ShippingReleaseRepository shippingReleaseRepository, final ShippingOrderRepository orderRepository) {
    this.shippingReleaseRepository = shippingReleaseRepository;
    this.orderRepository = orderRepository;
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

  public List<String> findLoads(String load) {
    List<String> loads = hash.get(Category.LOAD);
    return loads;
  }

}
