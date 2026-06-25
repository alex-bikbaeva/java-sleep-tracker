package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinDurationTest {
    @Test
    void shouldReturnMinDuration() {
        MinDurationAnalysis minDurationAnalysis = new MinDurationAnalysis();

        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2026, 3, 1, 22, 0),
                        LocalDateTime.of(2026, 3, 2, 6, 0),
                        SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2026, 3, 3, 13, 0),
                        LocalDateTime.of(2026, 3, 3, 14, 0),
                        SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2026, 3, 5, 16, 30),
                        LocalDateTime.of(2026, 3, 5, 17, 0),
                        SleepQuality.NORMAL)
        );

        SleepAnalysisResult result = minDurationAnalysis.apply(sleepingSessions);

        assertEquals(30L, result.getValue());
    }

    @Test
    void shouldReturnZeroAsMinDurationForEmptyList() {
        MinDurationAnalysis minDurationAnalysis = new MinDurationAnalysis();

        SleepAnalysisResult result = minDurationAnalysis.apply(List.of());

        assertEquals(0L, result.getValue());
    }
}
