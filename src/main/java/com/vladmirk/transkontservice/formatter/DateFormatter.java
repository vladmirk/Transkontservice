package com.vladmirk.transkontservice.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {
  SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

  @Override
  public Date parse(String text, Locale locale) throws ParseException {
    try {
      return df.parse(text);
    } catch (ParseException e) {
      return null;
    }
  }
  @Override
  public String print(Date date, Locale locale) {
    return df.format(date);
  }
}
