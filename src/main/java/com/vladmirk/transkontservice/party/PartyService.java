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
import java.util.stream.Collectors;

import static com.vladmirk.transkontservice.party.PartyType.LOAD;
import static org.springframework.util.StringUtils.isEmpty;

@Service
public class PartyService {
  private ExpeditorRepository expeditorRepository;
  private LoadRepository loadRepository;
  private UnloadRepository unloadRepository;
  private static Map<PartyType, List<SimpleParty>> partyCache = Collections
      .synchronizedMap(new EnumMap<PartyType, List<SimpleParty>>(PartyType.class));

  @Autowired
  public PartyService(ExpeditorRepository expeditorRepository, LoadRepository loadRepository, UnloadRepository unloadRepository) {
    this.expeditorRepository = expeditorRepository;
    this.loadRepository = loadRepository;
    this.unloadRepository = unloadRepository;
  }

  @PostConstruct
  public void init() {
    Arrays.stream(PartyType.values()).forEach((p) -> partyCache.put(p, new ArrayList<>()));

    loadRepository.findAll().forEach(partyCache.get(LOAD)::add);
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

  public Load saveLoader(Load load) {
    Load l = loadRepository.save(load);
    updatePartyCache(PartyType.LOAD, l);
    return l;
  }

  public Unload saveUnloader(Unload unload) {
    Unload u = unloadRepository.save(unload);
    updatePartyCache(PartyType.UNLOAD, u);
    return u;
  }

  private void updatePartyCache(PartyType type, SimpleParty party) {
    int i = partyCache.get(type).indexOf(party);
    if (i > -1) {
      partyCache.get(type).set(i, party);
    } else
      partyCache.get(type).add(party);
  }

  public Load saveOrCreateNew(Load load) {
    if (load.getId() != null && !isEmpty(load.getName())) {
      Optional<Load> l = loadRepository.findById(load.getId());
      if (l.isPresent() && l.get().getName().equalsIgnoreCase(load.getName())) {
        l.get().setName(load.getName());
        return saveLoader(l.get());
      }
    }
    return saveLoader(new Load(load.getName()));
  }

  public Unload saveOrCreateNew(Unload unload) {
    if (unload.getId() != null && !isEmpty(unload.getName())) {
      Optional<Unload> u = unloadRepository.findById(unload.getId());
      if (u.isPresent() && u.get().getName().equalsIgnoreCase(unload.getName())) {
        u.get().setName(unload.getName());
        return saveUnloader(u.get());
      }
    }
    return saveUnloader(new Unload(unload.getName()));
  }

  public List<SimpleParty> findParty(PartyType type, String name) {
    List<? extends SimpleParty> simpleParties = partyCache.get(type);
    return simpleParties.stream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
  }

}
