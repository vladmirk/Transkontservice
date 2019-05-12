package com.vladmirk.transkontservice.party;

import com.vladmirk.transkontservice.AppRuntimeException;
import com.vladmirk.transkontservice.Exceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class PartyService {
  private ExpeditorRepository expeditorRepository;
  private PartyNameRepository partyNameRepository;
  private static Map<PartyType, List<PartyName>> partyCache = Collections.synchronizedMap(new EnumMap<PartyType, List<PartyName>>(PartyType.class));

  @Autowired
  public PartyService(ExpeditorRepository expeditorRepository, PartyNameRepository partyNameRepository) {
    this.expeditorRepository = expeditorRepository;
    this.partyNameRepository = partyNameRepository;
  }

  @PostConstruct
  public void init() {
    Arrays.stream(PartyType.values()).forEach((p) -> partyCache.put(p, new ArrayList<>()));

    Map<PartyType, List<PartyName>> collect = StreamSupport.stream(partyNameRepository.findAll().spliterator(), false)
        .collect(Collectors.groupingBy(PartyName::getType, Collectors.mapping(Function.identity(), Collectors.toList())));

    partyCache.putAll(collect);

  }

  public List<Expeditor> findExpeditors(String exp) {
    String s = '%' + exp + '%';
    s = s.toUpperCase();
    return expeditorRepository.findExpeditorsByCodeContainingOrNameContainingAllIgnoreCase(s, s);
  }

  public Expeditor findExpeditor(String code, String name) {
    if (!isEmpty(code) && !isEmpty(name)) {
      return expeditorRepository.findExpeditorByCodeContainingAndNameContainingAllIgnoreCase(code.toUpperCase(), name.toUpperCase());
    }
    if (!isEmpty(code))
      return findExpeditorByCode(code);
    else
      return findExpeditorByName(name);
  }

  public Expeditor findExpeditorByCode(String code) {
    if (!isEmpty(code))
      return expeditorRepository.findExpeditorByCodeIgnoreCase(code.toUpperCase());
    return null;
  }

  public Expeditor findExpeditorByName(String name) {
    if (!isEmpty(name))
      return expeditorRepository.findExpeditorByNameIgnoreCase(name.toUpperCase());
    return null;
  }

  public Expeditor newExpeditor(String code, String name) {
    if (isEmpty(code) || isEmpty(name))
      throw new AppRuntimeException(Exceptions.EXPEDITOR_NEW_CODE_AND_NAME_MISSING);
    return expeditorRepository.save(new Expeditor(code, name));
  }

  public PartyName savePartyName(PartyName partyName) {
    PartyName save = partyNameRepository.save(partyName);
    updatePartyCache(partyName);
    return save;
  }


  private void updatePartyCache(PartyName party) {
    int i = partyCache.get(party.getType()).indexOf(party);
    if (i > -1) {
      partyCache.get(party.getType()).set(i, party);
    } else
      partyCache.get(party.getType()).add(party);
  }

  public PartyName savePartyNameOrCreateNew(PartyType type, PartyName partyName) {
    if (partyName.getId() != null && !isEmpty(partyName.getName())) {
      Optional<PartyName> pn = partyNameRepository.findById(partyName.getId());
      if (pn.isPresent() && pn.get().getName().equalsIgnoreCase(partyName.getName())) {
        pn.get().setName(partyName.getName());
        return savePartyName(pn.get());
      }
    }
    return savePartyName(new PartyName(type, partyName.getName()));
  }


  public List<PartyName> getPartyNames(PartyType type) {
    return partyCache.get(type);
  }

  public List<PartyName> findPartyName(PartyType type, String name) {
    List<PartyName> parties = getPartyNames(type);
    return parties.stream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
  }


}
