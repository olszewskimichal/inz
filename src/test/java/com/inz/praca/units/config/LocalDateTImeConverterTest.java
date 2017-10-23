package com.inz.praca.units.config;

import com.inz.praca.UnitTest;
import com.inz.praca.config.LocalDateTimeConverter;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class LocalDateTImeConverterTest {

    private final LocalDateTimeConverter converter = new LocalDateTimeConverter();

    @Test
    public void shouldConvertLocalDateTimeToTimestamp() {
        Timestamp timestamp = this.converter.convertToDatabaseColumn(LocalDateTime.now());
        assertThat(timestamp).isNotNull().isBeforeOrEqualsTo(new Date());

        timestamp = this.converter.convertToDatabaseColumn(null);
        assertThat(timestamp).isNull();
    }

    @Test
    public void shouldConvertTimestampToLocalDateTime() {
        LocalDateTime dateTime = this.converter.convertToEntityAttribute(Timestamp.valueOf(LocalDateTime.now()));
        assertThat(dateTime).isNotNull().isBeforeOrEqualTo(LocalDateTime.now());

        dateTime = this.converter.convertToEntityAttribute(null);
        assertThat(dateTime).isNull();
    }
}
