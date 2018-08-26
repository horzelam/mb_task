package com.mobimeo.challenge.verspaetung.service;

import com.mobimeo.challenge.verspaetung.domain.ImmutableLineDelay;
import com.mobimeo.challenge.verspaetung.domain.Line;
import com.mobimeo.challenge.verspaetung.domain.LineDelay;
import com.mobimeo.challenge.verspaetung.domain.LineTime;
import com.mobimeo.challenge.verspaetung.domain.Stop;
import com.mobimeo.challenge.verspaetung.repository.DelaysRepo;
import com.mobimeo.challenge.verspaetung.repository.LinesRepo;
import com.mobimeo.challenge.verspaetung.repository.StopsRepo;
import com.mobimeo.challenge.verspaetung.repository.TimesRepo;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.immutables.value.internal.$guava$.base.$Preconditions.checkNotNull;

/**
 * Core Line service to handle :
 * * "vehicles for a given time and coordinates"
 * * "if given line is currently delayed"
 */
@Service
public class LineService {

    private final DelaysRepo delaysRepo;
    private final StopsRepo stopsRepo;
    private final TimesRepo timesRepo;
    private final LinesRepo linesRepo;
    private Map<Integer, LineDelay> lineWithDelay;

    /**
     * Constructor
     *
     * @param delaysRepo delays repository as {@link DelaysRepo}
     * @param stopsRepo  stops repository as {@link StopsRepo}
     * @param timesRepo  times repository as {@link TimesRepo}
     * @param linesRepo  lines repository as {@link LinesRepo}
     */
    public LineService(final DelaysRepo delaysRepo, final StopsRepo stopsRepo, final TimesRepo timesRepo, final LinesRepo linesRepo) {
        this.delaysRepo = checkNotNull(delaysRepo, "delaysRepo must be provided");
        this.stopsRepo = checkNotNull(stopsRepo, "stopsRepo must be provided");
        this.timesRepo = checkNotNull(timesRepo, "timesRepo must be provided");
        this.linesRepo = checkNotNull(linesRepo, "linesRepo must be provided");
    }

    /**
     * To initialize lazily internal cache with Lines + Delays info
     */
    private Map<Integer, LineDelay> getLineWithDelays() {
        if(this.lineWithDelay == null) {
            this.lineWithDelay = this.prepareLineWithDelayMap();
        }
        return this.lineWithDelay;
    }

    private Map<Integer, LineDelay> prepareLineWithDelayMap() {
        // assuming small data are small and static - fit into memory
        // combine Line with Delay information (if provided) and store inside service
        return linesRepo.getAll().stream()
                .map(line -> ImmutableLineDelay.builder().line(line).delayMin(delaysRepo.getDelayForLine(line.getName())).build())
                .collect(Collectors.toMap(delay -> delay.getLine().getId(), delay -> delay));
    }

    /**
     * Returns list of lines for a given timestamp and x,y coordinates
     *
     * @param timestamp lines filtering criteria: time
     * @param x         lines filtering criteria: x coordinate of a given stop
     * @param y         lines filtering criteria: y coordinate of a given stop
     *
     * @return lines matching given criteria
     */
    public Set<Line> getLines(final LocalTime timestamp, final int x, final int y) {
        // Note:assuming the datasets are small - we can collect everything in lists in memory

        // get Stops matching x,y params:
        final List<Integer> matchingStopsIds =
                stopsRepo.getAll().filter(candidate -> candidate.hasCoordinates(x, y)).map(Stop::stopId).collect(Collectors.toList());

        // get line Ids for line matching the time and stop criteria:
        return timesRepo.getAll()
                // get line tims info combined in Pair with line name and delay in single record
                .map(lineTimeCandidate -> Pair.of(lineTimeCandidate, getLineWithDelays().get(lineTimeCandidate.getLineId())))
                // filter by effective time compared to timestamp and Stop compared to maching Stops
                .filter(pair -> effectiveTime(pair).equals(timestamp) && matchingStopsIds.contains(pair.getLeft().getStopId()))
                // extract only Line info to collect:
                .map(pair -> pair.getRight().getLine()).collect(Collectors.toSet());
    }

    private LocalTime effectiveTime(final Pair<LineTime, LineDelay> lineTimeAndDelayInfo) {
        final LocalTime originalTime = lineTimeAndDelayInfo.getLeft().getTime();
        // if delay provided ...
        return lineTimeAndDelayInfo.getRight().getDelayMin()
                // .. time shifted by delay
                .map(originalTime::plusMinutes)
                // otherwise original time without delay
                .orElse(lineTimeAndDelayInfo.getLeft().getTime());
    }

}
