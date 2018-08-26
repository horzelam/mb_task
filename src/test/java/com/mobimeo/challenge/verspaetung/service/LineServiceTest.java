package com.mobimeo.challenge.verspaetung.service;

import com.mobimeo.challenge.verspaetung.domain.ImmutableLine;
import com.mobimeo.challenge.verspaetung.domain.ImmutableLineTime;
import com.mobimeo.challenge.verspaetung.domain.ImmutableStop;
import com.mobimeo.challenge.verspaetung.domain.Line;
import com.mobimeo.challenge.verspaetung.domain.Stop;
import com.mobimeo.challenge.verspaetung.repository.DelaysRepo;
import com.mobimeo.challenge.verspaetung.repository.LinesRepo;
import com.mobimeo.challenge.verspaetung.repository.StopsRepo;
import com.mobimeo.challenge.verspaetung.repository.TimesRepo;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.time.LocalTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class LineServiceTest {

    private static final Stop STOP_A = ImmutableStop.builder().stopId(1).x( 10).y(10).build();
    private static final Stop STOP_B = ImmutableStop.builder().stopId(2).x( 20).y(20).build();
    private static final Line LINE_FIRST = ImmutableLine.builder().id(1).name("First").build();
    private static final Line LINE_SECOND = ImmutableLine.builder().id(2).name("Second").build();

    @Mock
    private DelaysRepo delaysRepo;
    @Mock
    private StopsRepo stopsRepo;
    @Mock
    private TimesRepo timesRepo;
    @Mock
    private LinesRepo linesRepo;

    private LineService tested;

    @BeforeMethod
    public void prepare() {
        MockitoAnnotations.initMocks(this);
        tested = new LineService(delaysRepo, stopsRepo, timesRepo, linesRepo);

        when(stopsRepo.getAll()).thenReturn(Stream.of(STOP_A, STOP_B));
        when(timesRepo.getAll()).thenReturn(Stream.of(
                // stops for 1st line:
                ImmutableLineTime.builder().lineId(LINE_FIRST.getId()).stopId(STOP_A.stopId()).time(of(10, 10, 10)).build(),
                ImmutableLineTime.builder().lineId(LINE_FIRST.getId()).stopId(STOP_B.stopId()).time(of(11, 20, 0)).build(),
                ImmutableLineTime.builder().lineId(LINE_SECOND.getId()).stopId(STOP_B.stopId()).time(of(11, 20, 0)).build()

        ));
        when(linesRepo.getAll()).thenReturn(new HashSet<>(Arrays.asList(LINE_FIRST, LINE_SECOND)));
    }

    @Test(dataProvider = "cases")
    public void shouldFindLineForTimeAndCoordinatesWhenNoDelays(final LocalTime time, final int x, final int y, Set<Line> expectedLines) {
        // GIVEN no delays
        when(delaysRepo.getDelayForLine(anyString())).thenReturn(Optional.empty());

        // WHEN
        final Set<Line> result = tested.getLines(time, x, y);

        // THEN
        assertThat(result).hasSameElementsAs(expectedLines);
    }

    @DataProvider
    public Object[][] cases() {
        return new Object[][]{
                // INPUT : time/x/y matches 1st stop with 1 line -> expected: 1 line found
                {of(10, 10, 10), 10, 10, new HashSet<>(Arrays.asList(LINE_FIRST))},
                // INPUT : nothing matches -> expected: 0 lines found
                {of(11, 0, 0), 10, 10, new HashSet<>()},
                // INPUT : time/x/y matches 2nd stop with both lines -> expected: 2 lines found
                {of(11, 20, 0), 20, 20, new HashSet<>(Arrays.asList(LINE_FIRST, LINE_SECOND))},

        };
    }

    @Test
    public void shouldFindOnly2ndLineWhenFirstIsDelayed() {
        // GIVEN 1st line delayed by 1 minute
        when(delaysRepo.getDelayForLine(LINE_FIRST.getName())).thenReturn(Optional.of(1));
        when(delaysRepo.getDelayForLine(LINE_SECOND.getName())).thenReturn(Optional.empty());

        // WHEN asking about the lines for STOP B in Time which normally matches Both Lines but 1st Line is delayed
        final Set<Line> result = tested.getLines(of(11, 20, 0), 20, 20);

        // THEN we get only 2nd Line
        assertThat(result).hasSameElementsAs(new HashSet<>(Arrays.asList(LINE_SECOND)));
    }

    @Test
    public void shouldFindOnly1stLineWhenAskingInDelayedTime() {
        // GIVEN 1st line delayed by 1 minute
        when(delaysRepo.getDelayForLine(LINE_FIRST.getName())).thenReturn(Optional.of(1));
        when(delaysRepo.getDelayForLine(LINE_SECOND.getName())).thenReturn(Optional.empty());

        // WHEN asking about the lines for STOP B , BUT in Time shifted by 1 minute
        final Set<Line> result = tested.getLines(of(11, 21, 0), 20, 20);

        // THEN we get only 1st Line
        assertThat(result).hasSameElementsAs(new HashSet<>(Arrays.asList(LINE_FIRST)));
    }
}
