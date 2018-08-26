package com.mobimeo.challenge.verspaetung.repository.mappings;

import com.mobimeo.challenge.verspaetung.domain.ImmutableStop;
import com.mobimeo.challenge.verspaetung.domain.Stop;

import java.util.function.Function;

import static java.lang.Integer.parseInt;

/**
 * Maps the Stop CSV data to Stop representation.
 */
public class StopMapper implements Function<String[], Stop> {

    @Override
    public Stop apply(final String[] split) {
        return ImmutableStop.builder().stopId(parseInt(split[0])).x(parseInt(split[1])).y(parseInt(split[2])).build();
    }
}