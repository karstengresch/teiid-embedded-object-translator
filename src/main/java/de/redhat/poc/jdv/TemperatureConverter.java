package de.redhat.poc.jdv;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Karsten Gresch on 08.02.17.
 */
public class TemperatureConverter
{
  public static BigDecimal celsiusToFahrenheit(BigDecimal celsius) {
    assert celsius != null;
    assert celsius instanceof BigDecimal;

    Double minicelius = celsius.doubleValue();
    return BigDecimal.valueOf((9.0/5.0) * minicelius + 32);
  }

  public static List<Object> getManyValues(String theDummyId) {
    List<Object> result = new LinkedList<Object>();
    result.add(theDummyId);

    List<String> dummyStringList = Arrays.asList("Paul Celan", "Pablo Neruda", "Ingeborg Bachmann", "Thomas Kling");

    result.add(dummyStringList);

    Map<String, Object> dummyMap = new HashMap<String, Object>();
    dummyMap.put("parameter", theDummyId);
    dummyMap.put("dummyStringList", dummyStringList);

    result.add(dummyMap);

    return result;

  }


}
