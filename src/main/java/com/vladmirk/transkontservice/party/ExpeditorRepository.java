package com.vladmirk.transkontservice.party;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExpeditorRepository extends CrudRepository<Expeditor, Long> {

  Expeditor findByCode(String code);

  List<Expeditor> findAllByCodeLikeOrNameLike(String code, String name);

}
