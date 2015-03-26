package com.tempoiq.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.tempoiq.DeviceStatus;
import com.tempoiq.UpsertResponse;

public class UpsertResponseModule extends SimpleModule {
  public UpsertResponseModule() {
    addDeserializer(UpsertResponse.class, new UpsertResponseDeserializer());
  }

  private static class UpsertResponseDeserializer extends StdScalarDeserializer<UpsertResponse> {

    public UpsertResponseDeserializer() {
      super(UpsertResponse.class);
    }

    @Override
    public UpsertResponse deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      JsonNode node = jp.readValueAsTree(); 
      Iterator<String> fields = node.fieldNames();
      HashMap<String, DeviceStatus> statuses = new HashMap<String, DeviceStatus>();
      while (fields.hasNext()) {
        String key = fields.next();
        DeviceStatus status = Json.getObjectMapper().treeToValue(node.get(key), DeviceStatus.class);
        statuses.put(key, status);
      }
      return new UpsertResponse(statuses);
    }
  }

  @Override
  public String getModuleName() {
    return "deviceState";
  }

  @Override
  public Version version() {
    return Version.unknownVersion();
  }
}
