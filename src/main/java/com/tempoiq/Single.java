package com.tempoiq;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Single implements QueryAction {
  private final Integer limit;
  private final DirectionFunction function;
  private final boolean includeSelection;
  private final DateTime timestamp;

  public Single(DirectionFunction function, DateTime timestamp, int limit, boolean includeSelection) {
    this.function = function;
    this.timestamp = timestamp;
    this.limit = limit;
    this.includeSelection = includeSelection;
  }

  public Single(DirectionFunction function) {
    if (function.equals(DirectionFunction.EARLIEST) || function.equals(DirectionFunction.LATEST)) {
      this.function = function;
      this.timestamp = null;
      this.limit = null;
      this.includeSelection = false;
    } else {
      throw new IllegalArgumentException("Cannot call single without a timestamp except for earliest or latest");
    }
  }

  public Single(DirectionFunction function, DateTime timestamp) {
    this.function = function;
    this.timestamp = timestamp;
    this.limit = null;
    this.includeSelection = false;
  }

  public Single(DirectionFunction function, DateTime timestamp, int limit) {
    this.function = function;
    this.timestamp = timestamp;
    this.limit = limit;
    this.includeSelection = false;
  }

  public Single() {
    this.function = DirectionFunction.LATEST;
    this.timestamp = null;
    this.limit = null;
    this.includeSelection = false;
  }

  @JsonIgnore
  public final String getName() {
    return "single";
  }


  @JsonProperty("limit")
  public Integer getLimit() {
    return this.limit;
  }

  @JsonProperty("function")
  public DirectionFunction getFunction() {
    return this.function;
  }

  @JsonProperty("timestamp")
  public DateTime getTimestamp() {
    return this.timestamp;
  }
}
