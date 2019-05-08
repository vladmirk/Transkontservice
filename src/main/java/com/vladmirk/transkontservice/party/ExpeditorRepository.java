package com.vladmirk.transkontservice.party;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExpeditorRepository extends CrudRepository<Expeditor, Long> {

  Expeditor findByCode(String code);

//  List<Expeditor> findExpeditorsByByCodeLikeOrNameLike(String code, String name);

  List<Expeditor> findExpeditorsByCodeContainingOrNameContainingAllIgnoreCase(String code, String name);
  Expeditor findExpeditorByCodeContainingAndNameContainingAllIgnoreCase(String code, String name);
  Expeditor findExpeditorByCodeIgnoreCase(String code);
  Expeditor findExpeditorByNameIgnoreCase(String name);

}
