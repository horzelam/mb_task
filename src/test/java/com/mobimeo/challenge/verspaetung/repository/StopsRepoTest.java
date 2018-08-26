package com.mobimeo.challenge.verspaetung.repository;

import com.mobimeo.challenge.verspaetung.domain.ImmutableStop;
import com.mobimeo.challenge.verspaetung.domain.Stop;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class StopsRepoTest {

    @Test
    public void shouldExposeReadCSVData() throws IOException {
        // GIVEN
        final Resource resource = new DefaultResourceLoader().getResource("classpath:stops.csv");

        // WHEN
        final StopsRepo tested = new StopsRepo(resource);

        // THEN
        assertThat(tested.getAll().collect(Collectors.toSet()))
                // has size of
                .hasSize(12)
                // and random check on some elements:
                .contains(ImmutableStop.builder().stopId(0).x(1).y(1).build(), ImmutableStop.builder().stopId(1).x(1).y(4).build(),
                        ImmutableStop.builder().stopId(11).x(5).y(7).build());

    }

}

