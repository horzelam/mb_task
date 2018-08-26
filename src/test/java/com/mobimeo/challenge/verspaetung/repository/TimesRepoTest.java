package com.mobimeo.challenge.verspaetung.repository;

import com.mobimeo.challenge.verspaetung.domain.ImmutableLineTime;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalTime;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TimesRepoTest {

    @Test
    public void shouldExposeReadCSVData() throws IOException {
        // GIVEN
        final Resource resource = new DefaultResourceLoader().getResource("classpath:times.csv");

        // WHEN
        final TimesRepo tested = new TimesRepo(resource);

        // THEN
        assertThat(tested.getAll().collect(Collectors.toSet()))
                // has 15 elements
                .hasSize(15)
                // and random check on some elements
                .contains(ImmutableLineTime.builder().lineId(0).stopId(0).time(LocalTime.parse("10:00:00")).build(),
                        ImmutableLineTime.builder().lineId(0).stopId(1).time(LocalTime.parse("10:02:00")).build(),
                        ImmutableLineTime.builder().lineId(2).stopId(11).time(LocalTime.parse("10:15:00")).build());

    }

}

