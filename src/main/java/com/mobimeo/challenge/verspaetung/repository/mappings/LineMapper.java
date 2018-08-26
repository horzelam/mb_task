package com.mobimeo.challenge.verspaetung.repository.mappings;

import com.mobimeo.challenge.verspaetung.domain.ImmutableLine;
import com.mobimeo.challenge.verspaetung.domain.Line;

import java.util.function.Function;

/**
 * Repository to manage the Line data.
 */
public class LineMapper implements Function<String[], Line> {

    @Override
    public Line apply(final String[] split) {
        return ImmutableLine.builder().id(Integer.parseInt(split[0])).name(split[1]).build();
    }
}
