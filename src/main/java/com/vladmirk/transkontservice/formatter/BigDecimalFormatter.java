package com.vladmirk.transkontservice.formatter;

import org.springframework.format.Formatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class BigDecimalFormatter implements Formatter<BigDecimal> {
  private static DecimalFormatSymbols SYMBOLS;
  static {
    SYMBOLS = new DecimalFormatSymbols();
    SYMBOLS.setDecimalSeparator('.');
    SYMBOLS.setGroupingSeparator(' ');
  }
  public static final DecimalFormat NUMERIC_FORMAT = new DecimalFormat("#,##0.00", SYMBOLS);

  @Override
  public BigDecimal parse(String text, Locale locale) throws ParseException {
    NUMERIC_FORMAT.setParseBigDecimal(true);
    return (BigDecimal) NUMERIC_FORMAT.parse(text);
  }

  @Override
  public String print(BigDecimal object, Locale locale) {
    return NUMERIC_FORMAT.format(object);
  }
}
