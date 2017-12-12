package com.inz.praca.units;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestStringUtil {

  public static String createStringWithLength(int length) {
    return IntStream.range(0, length).mapToObj(index -> "a").collect(Collectors.joining());
  }
}
