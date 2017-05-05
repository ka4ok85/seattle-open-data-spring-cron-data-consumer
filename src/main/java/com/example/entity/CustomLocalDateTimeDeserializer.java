package com.example.entity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

public class CustomLocalDateTimeDeserializer extends StdScalarDeserializer<LocalDateTime> {

	private static final long serialVersionUID = 6686896957330281122L;

	public CustomLocalDateTimeDeserializer() {
          super(LocalDateTime.class);
    }
    
	@Override
	public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		
		long unixSeconds = p.getValueAsLong();

		Date date = new Date(unixSeconds*1000L);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String formattedDate = sdf.format(date);
				
		System.out.println(formattedDate);
		
		return LocalDateTime.parse(formattedDate);
	}
}