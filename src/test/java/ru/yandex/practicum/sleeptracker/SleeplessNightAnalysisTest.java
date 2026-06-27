package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleeplessNightAnalysisTest {
    @Test
    void shouldReturnZeroSleeplessNightsWhenEveryNightHasSleep() {
        SleeplessNightsAnalysis analysis = new SleeplessNightsAnalysis();

        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 30),
                        LocalDateTime.of(2025, 10, 3, 6, 30),
                        SleepQuality.NORMAL
                )
        );

        SleepAnalysisResult result = analysis.apply(sessions);

        assertEquals(0L, result.getValue());
    }

    @Test
    void shouldReturnOneSleeplessNight() {
        SleeplessNightsAnalysis analysis = new SleeplessNightsAnalysis();

        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 30),
                        LocalDateTime.of(2025, 10, 4, 6, 30),
                        SleepQuality.NORMAL
                )
        );

        SleepAnalysisResult result = analysis.apply(sessions);

        assertEquals(1L, result.getValue());
    }

    @Test
    void shouldIgnoreDaySleepForSleeplessNightCalculation() {
        SleeplessNightsAnalysis analysis = new SleeplessNightsAnalysis();

        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 14, 0),
                        LocalDateTime.of(2025, 10, 1, 15, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.GOOD
                )
        );

        SleepAnalysisResult result = analysis.apply(sessions);

        assertEquals(1L, result.getValue());
    }

    @Test
    void shouldHandleCrossMonthSessions() {
        SleeplessNightsAnalysis analysis = new SleeplessNightsAnalysis();

        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 9, 30, 23, 30),
                        LocalDateTime.of(2025, 10, 1, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.NORMAL
                )
        );

        SleepAnalysisResult result = analysis.apply(sessions);

        assertEquals(1L, result.getValue());
    }

    @Test
    void shouldReturnZeroSleeplessNightsForEmptySessions() {
        SleeplessNightsAnalysis analysis = new SleeplessNightsAnalysis();

        SleepAnalysisResult result = analysis.apply(List.of());

        assertEquals(0L, result.getValue());
    }
}
