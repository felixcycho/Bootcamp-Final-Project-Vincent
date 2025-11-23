package com.bootcamp.demo.project_data_provider.lib;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class UnixToHKTimeDeserializer extends JsonDeserializer<LocalDateTime> {
  @Override
  public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    long epochSeconds = p.getLongValue();
    return LocalDateTime.ofInstant(
      Instant.ofEpochSecond(epochSeconds),
      ZoneId.of("Asia/Hong_Kong")
    );
  }
}
