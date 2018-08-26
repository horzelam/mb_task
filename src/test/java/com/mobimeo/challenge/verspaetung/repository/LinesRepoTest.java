package com.mobimeo.challenge.verspaetung.repository;

import com.mobimeo.challenge.verspaetung.domain.ImmutableLine;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class LinesRepoTest {

    @Test
    public void shouldExposeReadCSVData() throws IOException {
        // GIVEN
        final Resource resource = new DefaultResourceLoader().getResource("classpath:lines.csv");

        // WHEN
        final LinesRepo tested = new LinesRepo(resource);

        // THEN
        assertThat(tested.getAll()).isNotNull().containsExactlyElementsOf(Arrays.asList(
                // contains exactly following elements in any order
                ImmutableLine.builder().id(1).name("200").build(), ImmutableLine.builder().id(2).name("S75").build(),
                ImmutableLine.builder().id(0).name("M4").build()));

    }

}

