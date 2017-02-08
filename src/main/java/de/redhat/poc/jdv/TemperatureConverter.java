package de.redhat.poc.jdv;

import java.math.BigDecimal;

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


}
