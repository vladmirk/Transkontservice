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

import static com.vladmirk.transkontservice.party.PartyType.LOADER;
import static org.springframework.util.StringUtils.isEmpty;

@Service
public class PartyService {
  private ExpeditorRepository expeditorRepository;
  private LoaderRepository loaderRepository;
  private static Map<PartyType, List<SimpleParty>> partyCache = Collections
      .synchronizedMap(new EnumMap<PartyType, List<SimpleParty>>(PartyType.class));

  @Autowired
  public PartyService(ExpeditorRepository expeditorRepository, LoaderRepository loaderRepository) {
    this.expeditorRepository = expeditorRepository;
    this.loaderRepository = loaderRepository;
  }

  @PostConstruct
  public void init() {
    Arrays.stream(PartyType.values()).forEach((p) -> partyCache.put(p, new ArrayList<>()));

    loaderRepository.findAll().forEach(partyCache.get(LOADER)::add);
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

  public Loader saveLoader(Loader loader) {
    Loader l = loaderRepository.save(loader);
    updatePartyCache(PartyType.LOADER, l);
    return l;
  }
  private void updatePartyCache(PartyType type, Loader loader) {
    int i = partyCache.get(type).indexOf(loader);
    if (i > -1) {
      partyCache.get(type).set(i, loader);
    } else
      partyCache.get(type).add(loader);
  }

  public Loader saveOrCreateNew(Loader loader) {
    if (loader.getId() != null && !isEmpty(loader.getName())) {
      Optional<Loader> l = loaderRepository.findById(loader.getId());
      if (l.isPresent() && l.get().getName().equalsIgnoreCase(loader.getName())) {
        l.get().setName(loader.getName());
        return saveLoader(l.get());
      }
    }
    return saveLoader(new Loader(loader.getName()));
  }

  public List<SimpleParty> findParty(PartyType type, String name) {
    List<? extends SimpleParty> simpleParties = partyCache.get(type);
    return simpleParties.stream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
  }

}
