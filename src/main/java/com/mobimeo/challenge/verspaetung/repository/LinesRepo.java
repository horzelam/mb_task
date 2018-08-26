package com.mobimeo.challenge.verspaetung.repository;

import com.mobimeo.challenge.verspaetung.domain.Line;
import com.mobimeo.challenge.verspaetung.repository.mappings.LineMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Set;

import static com.mobimeo.challenge.verspaetung.repository.utils.MappingUtils.readAllData;

@Repository
public class LinesRepo {

    private final Set<Line> data;

    /**
     * Constructor
     *
     * @param resource resources from which data are read
     *
     * @throws IOException if failed to read the data
     */
    public LinesRepo(@Value("${data.lines}") Resource resource) throws IOException {
        this.data = readAllData(resource, new LineMapper());
    }

    public Set<Line> getAll() {
        return data;
    }
}
