package com.mobimeo.challenge.verspaetung.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * Represents information about given line
 * Obtained from "lines.csv"
 */
@JsonSerialize(as = ImmutableLine.class)
@JsonDeserialize(as = ImmutableLine.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Immutable
@Value.Style(jdkOnly = true)
public interface Line {

    int getId();

    String getName();
}
