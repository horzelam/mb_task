package com.mobimeo.challenge.verspaetung.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * Represents information about line's delay as it is contained in files/repo
 * It has lineName but for more use cases it could be refactored to be organized by lineId (if we assume that we don't have inconsistency delay.csv<->lines.csv)
 * Obtained from "delays.csv".
 */
@JsonSerialize(as = ImmutableDelayRAW.class)
@JsonDeserialize(as = ImmutableDelayRAW.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Immutable
@Value.Style(jdkOnly = true)
public interface DelayRAW {

    String getLineName();

    int getDelayMin();

}
