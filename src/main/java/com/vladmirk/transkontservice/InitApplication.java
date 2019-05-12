package com.vladmirk.transkontservice;

import com.vladmirk.transkontservice.party.Expeditor;
import com.vladmirk.transkontservice.party.ExpeditorRepository;
import com.vladmirk.transkontservice.party.Load;
import com.vladmirk.transkontservice.party.LoadRepository;
import com.vladmirk.transkontservice.party.PartyService;
import com.vladmirk.transkontservice.party.Unload;
import com.vladmirk.transkontservice.party.UnloadRepository;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InitApplication {

  private ExpeditorRepository expeditorRepository;
  private ShippingOrderRepository shippingOrderRepository;
  private ShippingReleaseRepository shippingReleaseRepository;
  private LoadRepository loadRepository;
  private PartyService partyService;
  private UnloadRepository unloadRepository;
  @Bean
  @Profile("dev")
  @Autowired
  CommandLineRunner setUp(final ExpeditorRepository expeditorRepository, final ShippingOrderRepository shippingOrderRepository,
      final ShippingReleaseRepository shippingReleaseRepository, final LoadRepository loadRepository, PartyService partyService,
      final UnloadRepository unloadRepository) {
    this.expeditorRepository = expeditorRepository;
    this.shippingOrderRepository = shippingOrderRepository;
    this.shippingReleaseRepository = shippingReleaseRepository;
    this.loadRepository = loadRepository;
    this.partyService = partyService;
    this.unloadRepository = unloadRepository;
    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Init the app");

    return (args) -> {
      System.out.println("Dev profile");
      Expeditor expeditor = createExpeditor();
      List<Load> loads = createLoaders();
      List<Unload> unloads = createUnloaders();
      createShippingOrders(expeditor, loads, unloads);
      partyService.init();
    };
  }
  private List<Unload> createUnloaders() {
    Unload u1 = new Unload("150030, Ярославская обл., г.Ярославль, ул.Пожарского, 66");
    partyService.saveUnloader(u1);

    Unload u2 = new Unload("610035, г.Киров, ул. Техническая, д. 15");
    partyService.saveUnloader(u2);
    return (Arrays.asList(u1, u2));
  }

  private List<Load> createLoaders() {
    Load l1 = new Load("Автотерминал АО \"Газпромнефть МЗСМ\"");
    l1 = loadRepository.save(l1);
    Load l2 = new Load("Автотерминал склада ООО \"ОК Логистика\" (г. Нижний Новгород)");
    l2 = loadRepository.save(l2);
    return Arrays.asList(l1, l2);
  }

  private void createShippingOrders(Expeditor expeditor, List<Load> loads, List<Unload> unloads) {
    ShippingOrder o = new ShippingOrder();
    o.setOrderDate(new Date());
    o.setOrderNumber("11112");
    o.setExpeditor(expeditor);
    shippingOrderRepository.save(o);

    ShippingRelease sr = new ShippingRelease();
    sr.setLoad(loads.get(0));
    sr.setUnload(unloads.get(0));
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
    sr1.setLoad(loads.get(1));
    sr1.setUnload(unloads.get(1));
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
