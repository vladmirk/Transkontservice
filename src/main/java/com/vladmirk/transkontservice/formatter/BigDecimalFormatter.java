package com.vladmirk.transkontservice.formatter;

import org.springframework.format.Formatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class BigDecimalFormatter implements Formatter<BigDecimal> {
  static public final NumberFormat NUMERIC_FORMAT = new DecimalFormat("# ##0.00");

  @Override
  public BigDecimal parse(String text, Locale locale) throws ParseException {
    return new BigDecimal(text);
  }
  @Override
  public String print(BigDecimal object, Locale locale) {
    return NUMERIC_FORMAT.format(object);
  }
}
