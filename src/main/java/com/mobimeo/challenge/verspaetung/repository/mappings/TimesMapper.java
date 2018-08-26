package com.mobimeo.challenge.verspaetung.repository.mappings;

import com.mobimeo.challenge.verspaetung.domain.ImmutableLineTime;
import com.mobimeo.challenge.verspaetung.domain.LineTime;

import java.time.LocalTime;
import java.util.function.Function;

import static java.lang.Integer.parseInt;

/**
 * Maps the Times CSV data to LineTime representation.
 */
public class TimesMapper implements Function<String[], LineTime> {

    @Override
    public LineTime apply(final String[] split) {
        return ImmutableLineTime.builder()
                // lineId
                .lineId(parseInt(split[0]))
                // stopId
                .stopId(parseInt(split[1]))
                // and time:
                .time(LocalTime.parse(split[2])).build();
    }
}
