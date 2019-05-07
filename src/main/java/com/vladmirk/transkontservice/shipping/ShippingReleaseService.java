package com.vladmirk.transkontservice.shipping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingReleaseService {
  private ShippingReleaseRepository shippingReleaseRepository;

  @Autowired
  public ShippingReleaseService(final ShippingReleaseRepository shippingReleaseRepository) {
    this.shippingReleaseRepository = shippingReleaseRepository;
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

}
