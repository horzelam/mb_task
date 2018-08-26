package com.mobimeo.challenge.verspaetung.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.time.LocalTime;

/**
 * Represents information about where given line stops on given time.
 * Obtained from "times.csv"
 */
@JsonSerialize(as = ImmutableLineTime.class)
@JsonDeserialize(as = ImmutableLineTime.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Immutable
@Value.Style(jdkOnly = true)
public interface LineTime {

    int getLineId();

    int getStopId();

    LocalTime getTime();

}
