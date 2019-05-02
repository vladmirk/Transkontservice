package com.vladmirk.transkontservice;

import com.vladmirk.transkontservice.party.Expeditor;
import com.vladmirk.transkontservice.party.ExpeditorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InitApplication {

  @Bean
  @Profile("dev")
  CommandLineRunner setUp(final ExpeditorRepository expeditorRepository) {
    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Init the app");

    return (args) -> {
      System.out.println("Dev profile");
      createExpeditor(expeditorRepository);
    };
  }
  private void createExpeditor(ExpeditorRepository expeditorRepository) {
    assert expeditorRepository != null;

    expeditorRepository.save(new Expeditor("00-00006612", "ООО \"ТКС\""));

  }

}
