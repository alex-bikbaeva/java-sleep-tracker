package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadQualityCountTest {
    @Test
    void shouldReturnBadQualitySessions() {
        BadQualityCountAnalysis badQualityCountAnalysis = new BadQualityCountAnalysis();

        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2026, 3, 1, 22, 0),
                        LocalDateTime.of(2026, 3, 2, 8, 0),
                        SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2026, 3, 3, 13, 0),
                        LocalDateTime.of(2026, 3, 3, 14, 0),
                        SleepQuality.BAD),
                new SleepingSession(LocalDateTime.of(2026, 3, 5, 16, 30),
                        LocalDateTime.of(2026, 3, 5, 17, 0),
                        SleepQuality.BAD)
        );

        SleepAnalysisResult result = badQualityCountAnalysis.apply(sleepingSessions);

        assertEquals(2L, result.getValue());
    }

    @Test
    void shouldReturnZeroAsBadQualitySessionsForEmptyList() {
         BadQualityCountAnalysis badQualityCountAnalysis = new BadQualityCountAnalysis();

        SleepAnalysisResult result = badQualityCountAnalysis.apply(List.of());

        assertEquals(0L, result.getValue());
    }
}
