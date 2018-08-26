package com.mobimeo.challenge.verspaetung.repository;

import com.mobimeo.challenge.verspaetung.domain.DelayRAW;
import com.mobimeo.challenge.verspaetung.repository.mappings.DelayRAWMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static com.mobimeo.challenge.verspaetung.repository.utils.MappingUtils.readAllData;

/**
 * Repository to manage the Delays data.
 */
@Repository
public class DelaysRepo {

    private final Set<DelayRAW> data;

    /**
     * Constructor
     *
     * @param resource resources from which data are read
     *
     * @throws IOException if failed to read the data
     */
    public DelaysRepo(@Value("${data.delays}") Resource resource) throws IOException {
        this.data = readAllData(resource, new DelayRAWMapper());
    }

    /**
     * Returns delay in minutes if it's defined for line or Optional.empty() if not
     *
     * @param name name of line
     *
     * @return optional delay in minutes
     */
    public Optional<Integer> getDelayForLine(final String name) {
        return data.stream().filter(delay -> delay.getLineName().equals(name)).map(DelayRAW::getDelayMin).findFirst();
    }
}
