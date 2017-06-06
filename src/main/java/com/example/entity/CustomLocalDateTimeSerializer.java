package com.example.entity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

public class CustomLocalDateTimeSerializer extends StdScalarSerializer<LocalDateTime> {

	public CustomLocalDateTimeSerializer() {
		super(LocalDateTime.class);
	}

	protected CustomLocalDateTimeSerializer(Class<LocalDateTime> t) {
		super(t);
	}

	private static final long serialVersionUID = 6503183608745563190L;

	@Override
	public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider provide) throws IOException {
		ZoneId zoneId = ZoneId.of("America/Los_Angeles");
		long timestamp = value.atZone(zoneId).toEpochSecond();
		generator.writeString(Long.toString(timestamp));
	}

}
