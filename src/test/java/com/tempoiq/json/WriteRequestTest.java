package com.tempoiq.json;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

import com.tempoiq.DataPoint;
import com.tempoiq.Device;
import com.tempoiq.Sensor;
import com.tempoiq.WriteRequest;


public class WriteRequestTest {
  private static final Device device = new Device("key1");
  private static final Sensor sensor = new Sensor("key1");

  @Test
  public void testSerializeUTC() throws IOException {
    DateTimeZone timezone = DateTimeZone.UTC;
    DataPoint dp = new DataPoint(new DateTime(2012, 1, 1, 0, 0, 1, 0, timezone), 12.34);
    WriteRequest wr = new WriteRequest().add(device, sensor, dp);

    String expected = "{\"key1\":{\"key1\":[{\"t\":\"2012-01-01T00:00:01.000Z\",\"v\":12.34}]}}";
    assertEquals(expected, Json.dumps(wr.asMap()));
  }

  @Test
  public void testSerializeTZ() throws IOException {
    DateTimeZone timezone = DateTimeZone.forID("America/Chicago");
    DataPoint dp = new DataPoint(new DateTime(2012, 1, 1, 0, 0, 1, 0, timezone), 12.34);
    WriteRequest wr = new WriteRequest().add(device, sensor, dp);

    String expected = "{\"key1\":{\"key1\":[{\"t\":\"2012-01-01T00:00:01.000-06:00\",\"v\":12.34}]}}";
    assertEquals(expected, Json.dumps(wr.asMap()));
  }
}
