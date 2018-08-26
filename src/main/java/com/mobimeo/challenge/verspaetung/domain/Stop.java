package com.mobimeo.challenge.verspaetung.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * Represents information about coordinates of a given stop.
 * Obtained from "stops.csv"
 */
@JsonSerialize(as = ImmutableStop.class)
@JsonDeserialize(as = ImmutableStop.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Immutable
@Value.Style(jdkOnly = true)
public abstract class Stop {
    public abstract int stopId();

    public abstract int x();

    public abstract int y();

    public boolean hasCoordinates(final int x, final int y) {
        return x() == x && y() == y;
    }
}
