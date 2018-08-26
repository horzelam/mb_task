package com.mobimeo.challenge.verspaetung.repository;

import com.mobimeo.challenge.verspaetung.domain.LineTime;
import com.mobimeo.challenge.verspaetung.repository.mappings.TimesMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;

import static com.mobimeo.challenge.verspaetung.repository.utils.MappingUtils.readAllData;

@Repository
public class TimesRepo {

    private final Set<LineTime> data;

    /**
     * Constructor
     *
     * @param resource resources from which data are read
     *
     * @throws IOException if failed to read the data
     */
    public TimesRepo(@Value("${data.times}") Resource resource) throws IOException {
        this.data = readAllData(resource, new TimesMapper());
    }

    public Stream<LineTime> getAll() {
        return data.stream();

    }
}
