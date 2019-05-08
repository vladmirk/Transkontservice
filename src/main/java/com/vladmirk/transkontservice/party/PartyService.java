package com.vladmirk.transkontservice.party;

import com.vladmirk.transkontservice.AppRuntimeException;
import com.vladmirk.transkontservice.Exceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class PartyService {
  private ExpeditorRepository expeditorRepository;

  @Autowired
  public PartyService(ExpeditorRepository expeditorRepository) {
    this.expeditorRepository = expeditorRepository;
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
}
