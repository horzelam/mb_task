package com.mobimeo.challenge.verspaetung.resource;

import com.mobimeo.challenge.verspaetung.domain.Line;
import com.mobimeo.challenge.verspaetung.repository.DelaysRepo;
import com.mobimeo.challenge.verspaetung.service.LineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Main Application Resource class responsible for exposing the REST API.
 */
@RestController
@RequestMapping(path = "/", produces = APPLICATION_JSON_VALUE)
public class Resource {
    private static final Logger LOGGER = LoggerFactory.getLogger(Resource.class.getName());

    private final LineService lineService;
    private final DelaysRepo delaysRepo;

    /**
     * Constructor
     *
     * @param lineService line service as {@link LineService}
     * @param delaysRepo  delay repository as {@link DelaysRepo}
     */
    public Resource(final LineService lineService, final DelaysRepo delaysRepo) {
        this.lineService = lineService;
        this.delaysRepo = delaysRepo;
    }

    /**
     * Returns the vehicles for a given time and coordinates
     */
    @GetMapping(value = "/lines")
    public Set<Line> lines(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime timestamp, @RequestParam int x,
            @RequestParam int y) {
        LOGGER.info("GET lines - params: timestamp={}, x={}, y={}", timestamp, x, y);
        return lineService.getLines(timestamp, x, y);
    }

    /**
     * Returns Json : {"isDelayed": TRUE} if line is delayed. Otherwise Json : {"isDelayed": FALSE} is returned
     */
    @GetMapping(value = "/lines/{name}")
    public IsDelayedResponse isDelayed(@PathVariable String name) {
        LOGGER.info("GET isDelayed - params: name={}", name);
        return ImmutableIsDelayedResponse.builder().isDelayed(delaysRepo.getDelayForLine(name).map(t -> true).orElse(false)).build();
    }
}
