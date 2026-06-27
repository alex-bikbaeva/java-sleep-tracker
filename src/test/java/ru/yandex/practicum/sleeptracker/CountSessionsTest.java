package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountSessionsTest {

    @Test
    void shouldCountAllSessions() {
        CountSessionsAnalysis countSessionsAnalysis = new CountSessionsAnalysis();

        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 22, 15),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 8, 0),
                        SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 14, 30),
                        LocalDateTime.of(2025, 10, 3, 15, 30),
                        SleepQuality.BAD)
        );

        SleepAnalysisResult result = countSessionsAnalysis.apply(sleepingSessions);

        assertEquals("Количество сессий сна", result.getDescription());
        assertEquals(3, result.getValue());
    }

}
