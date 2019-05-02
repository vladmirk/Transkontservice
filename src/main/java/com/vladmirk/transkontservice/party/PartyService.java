package com.vladmirk.transkontservice.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyService {
  private ExpeditorRepository expeditorRepository;

  @Autowired
  public PartyService(ExpeditorRepository expeditorRepository) {
    this.expeditorRepository = expeditorRepository;
  }

  public List<Expeditor> findExpeditors(String exp) {
    String s = '%' + exp + '%';
    return expeditorRepository.findAllByCodeLikeOrNameLike(s, s);
  }

}
