package com.mobimeo.challenge.verspaetung.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Style;

@JsonSerialize(as = ImmutableIsDelayedResponse.class)
@JsonDeserialize(as = ImmutableIsDelayedResponse.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Immutable
@Style(jdkOnly = true)
public interface IsDelayedResponse {

    boolean isDelayed();
}

