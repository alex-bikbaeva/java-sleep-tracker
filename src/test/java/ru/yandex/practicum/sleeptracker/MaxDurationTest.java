package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxDurationTest {
    @Test
    void shouldReturnMaxDuration() {
        MaxDurationAnalysis maxDurationAnalysis = new MaxDurationAnalysis();

        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2026, 3, 1, 22, 0),
                        LocalDateTime.of(2026, 3, 2, 8, 0),
                        SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2026, 3, 3, 13, 0),
                        LocalDateTime.of(2026, 3, 3, 14, 0),
                        SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2026, 3, 5, 16, 30),
                        LocalDateTime.of(2026, 3, 5, 17, 0),
                        SleepQuality.NORMAL)
        );

        SleepAnalysisResult result = maxDurationAnalysis.apply(sleepingSessions);

        assertEquals(600L, result.getValue());
    }

    @Test
    void shouldReturnZeroAsMaxDurationForEmptyList() {
        MaxDurationAnalysis maxDurationAnalysis = new MaxDurationAnalysis();

        SleepAnalysisResult result = maxDurationAnalysis.apply(List.of());

        assertEquals(0L, result.getValue());
    }
}
