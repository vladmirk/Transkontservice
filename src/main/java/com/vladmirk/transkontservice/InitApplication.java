package com.vladmirk.transkontservice;

import com.vladmirk.transkontservice.party.Expeditor;
import com.vladmirk.transkontservice.party.ExpeditorRepository;
import com.vladmirk.transkontservice.party.PartyName;
import com.vladmirk.transkontservice.party.PartyService;
import com.vladmirk.transkontservice.party.PartyType;
import com.vladmirk.transkontservice.shipping.ShippingOrder;
import com.vladmirk.transkontservice.shipping.ShippingOrderRepository;
import com.vladmirk.transkontservice.shipping.ShippingRelease;
import com.vladmirk.transkontservice.shipping.ShippingReleaseRepository;
import com.vladmirk.transkontservice.shipping.ShippingReleaseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.vladmirk.transkontservice.party.PartyType.LOAD;
import static com.vladmirk.transkontservice.party.PartyType.UNLOAD;

@Service
public class InitApplication {

  private ExpeditorRepository expeditorRepository;
  private ShippingOrderRepository shippingOrderRepository;
  private ShippingReleaseRepository shippingReleaseRepository;
  private PartyService partyService;
  @Bean
  @Profile("dev")
  @Autowired
  CommandLineRunner setUp(final ExpeditorRepository expeditorRepository, final ShippingOrderRepository shippingOrderRepository,
      final ShippingReleaseRepository shippingReleaseRepository, PartyService partyService) {
    this.expeditorRepository = expeditorRepository;
    this.shippingOrderRepository = shippingOrderRepository;
    this.shippingReleaseRepository = shippingReleaseRepository;
    this.partyService = partyService;
    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Init the app");

    return (args) -> {
      System.out.println("Dev profile");
      Expeditor expeditor = createExpeditor();
      createLoaders();
      createUnloaders();
      createShippingOrders(expeditor);
      partyService.init();
    };
  }
  private void createUnloaders() {
    partyService.savePartyName(new PartyName(PartyType.UNLOAD, "150030, Ярославская обл., г.Ярославль, ул.Пожарского, 66"));
    partyService.savePartyName(new PartyName(PartyType.UNLOAD, "610035, г.Киров, ул. Техническая, д. 15"));
  }

  private void createLoaders() {
    partyService.savePartyName(new PartyName(LOAD, "Автотерминал АО \"Газпромнефть МЗСМ\""));
    partyService.savePartyName(new PartyName(LOAD, "Автотерминал склада ООО \"ОК Логистика\" (г. Нижний Новгород)"));
  }

  private void createShippingOrders(Expeditor expeditor) {
    ShippingOrder o = new ShippingOrder();
    o.setOrderDate(new Date());
    o.setOrderNumber("11112");
    o.setExpeditor(expeditor);
    shippingOrderRepository.save(o);

    ShippingRelease sr = new ShippingRelease();
    sr.setLoad(partyService.getPartyNames(LOAD).get(0));
    sr.setUnload(partyService.getPartyNames(UNLOAD).get(0));
    sr.setUnloadCity("Ярославль");
    sr.setDestination("ГП Волга-Консалтинг фасовка");
    try {
      sr.setAppointmentLoadDate(new SimpleDateFormat("dd.MM.yy").parse("28.01.19"));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    sr.setCalculatedCost(new BigDecimal(19728.82));
    sr.setOrder(o);
    sr.setStatus(ShippingReleaseStatus.DRAFT);
    shippingReleaseRepository.save(sr);


    ShippingOrder o2 = new ShippingOrder();
    o2.setOrderDate(new Date());
    o2.setOrderNumber("2222222");
    o2.setExpeditor(expeditor);
    shippingOrderRepository.save(o2);

    ShippingRelease sr1 = new ShippingRelease();
    sr1.setLoad(partyService.getPartyNames(LOAD).get(1));
    sr1.setUnload(partyService.getPartyNames(UNLOAD).get(1));
    sr1.setUnloadCity("Киров");
    sr1.setDestination("ООО \"МЕГА-ОЙЛ Киров\"\t");
    try {
      sr1.setAppointmentLoadDate(new SimpleDateFormat("dd.MM.yy").parse("28.01.19"));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    sr1.setCalculatedCost(new BigDecimal(51864.41));
    sr1.setStatus(ShippingReleaseStatus.DRAFT);
    sr1.setOrder(o2);
    shippingReleaseRepository.save(sr1);


  }
  private Expeditor createExpeditor() {
    assert expeditorRepository != null;

    return expeditorRepository.save(new Expeditor("00-00006612", "ООО \"ТКС\""));

  }

}
