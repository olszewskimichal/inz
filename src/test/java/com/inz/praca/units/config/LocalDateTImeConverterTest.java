package com.inz.praca.units.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.UnitTest;
import com.inz.praca.config.LocalDateTimeConverter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class LocalDateTImeConverterTest {

  private final LocalDateTimeConverter converter = new LocalDateTimeConverter();

  @Test
  public void shouldConvertLocalDateTimeToTimestamp() {
    Timestamp timestamp = converter.convertToDatabaseColumn(LocalDateTime.now());
    assertThat(timestamp).isNotNull().isBeforeOrEqualsTo(new Date());

    timestamp = converter.convertToDatabaseColumn(null);
    assertThat(timestamp).isNull();
  }

  @Test
  public void shouldConvertTimestampToLocalDateTime() {
    LocalDateTime dateTime = converter.convertToEntityAttribute(Timestamp.valueOf(LocalDateTime.now()));
    assertThat(dateTime).isNotNull().isBeforeOrEqualTo(LocalDateTime.now());

    dateTime = converter.convertToEntityAttribute(null);
    assertThat(dateTime).isNull();
  }
}
