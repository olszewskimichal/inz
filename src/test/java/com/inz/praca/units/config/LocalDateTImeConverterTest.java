package com.inz.praca.units.config;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import com.inz.praca.config.LocalDateTimeConverter;
import org.junit.Test;

public class LocalDateTImeConverterTest {

	LocalDateTimeConverter converter = new LocalDateTimeConverter();

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
		assertThat(dateTime).isNotNull().isLessThanOrEqualTo(LocalDateTime.now());

		dateTime = converter.convertToEntityAttribute(null);
		assertThat(dateTime).isNull();
	}
}
