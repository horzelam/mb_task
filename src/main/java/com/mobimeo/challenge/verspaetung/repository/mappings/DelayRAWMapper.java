package com.mobimeo.challenge.verspaetung.repository.mappings;

import com.mobimeo.challenge.verspaetung.domain.DelayRAW;
import com.mobimeo.challenge.verspaetung.domain.ImmutableDelayRAW;

import java.util.function.Function;

import static java.lang.Integer.parseInt;

/**
 * Maps the Delay CSV data to DelayRAW representation.
 */
public class DelayRAWMapper implements Function<String[], DelayRAW> {

    @Override
    public DelayRAW apply(final String[] split) {
        return ImmutableDelayRAW.builder().lineName(split[0]).delayMin(parseInt(split[1])).build();
    }
}
