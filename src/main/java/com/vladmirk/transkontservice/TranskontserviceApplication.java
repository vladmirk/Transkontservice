package com.vladmirk.transkontservice;

import com.vladmirk.transkontservice.formatter.BigDecimalFormatter;
import com.vladmirk.transkontservice.formatter.DateFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TranskontserviceApplication {

  public static void main(String[] args) {
    SpringApplication.run(TranskontserviceApplication.class, args);
  }

  @Configuration
  static class MyConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
      registry.addFormatter(new DateFormatter());
      registry.addFormatter(new BigDecimalFormatter());
    }
  }

}

