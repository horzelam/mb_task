package com.mobimeo.challenge.verspaetung.repository;

import com.mobimeo.challenge.verspaetung.domain.Stop;
import com.mobimeo.challenge.verspaetung.repository.mappings.StopMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;

import static com.mobimeo.challenge.verspaetung.repository.utils.MappingUtils.readAllData;

@Repository
public class StopsRepo {

    private final Set<Stop> data;

    /**
     * Constructor
     *
     * @param resource resources from which data are read
     *
     * @throws IOException if failed to read the data
     */
    public StopsRepo(@Value("${data.stops}") Resource resource) throws IOException {
        this.data = readAllData(resource, new StopMapper());
    }

    public Stream<Stop> getAll() {
        return data.stream();
    }
}
