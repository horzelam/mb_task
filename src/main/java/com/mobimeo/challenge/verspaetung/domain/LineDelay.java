package com.mobimeo.challenge.verspaetung.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Optional;

/**
 * Represents line combined with delay information (if provided).
 */
@JsonSerialize(as = ImmutableLineDelay.class)
@JsonDeserialize(as = ImmutableLineDelay.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Immutable
@Value.Style(jdkOnly = true)
public interface LineDelay {

    Line getLine();

    Optional<Integer> getDelayMin();

}
