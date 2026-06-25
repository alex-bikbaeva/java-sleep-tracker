package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChronotypeTest {
    @Test
    void shouldClassifyUserAsOwl() {
        ChronotypeAnalysis analysis = new ChronotypeAnalysis();

        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 45),
                        LocalDateTime.of(2025, 10, 3, 9, 40),
                        SleepQuality.NORMAL
                )
        );

        SleepAnalysisResult result = analysis.apply(sessions);

        assertEquals(Chronotype.OWL, result.getValue());
    }

    @Test
    void shouldClassifyUserAsLark() {
        ChronotypeAnalysis analysis = new ChronotypeAnalysis();

        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 30),
                        LocalDateTime.of(2025, 10, 2, 6, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 21, 15),
                        LocalDateTime.of(2025, 10, 3, 6, 20),
                        SleepQuality.NORMAL
                )
        );

        SleepAnalysisResult result = analysis.apply(sessions);

        assertEquals(Chronotype.LARK, result.getValue());
    }

    @Test
    void shouldClassifyUserAsDove() {
        ChronotypeAnalysis analysis = new ChronotypeAnalysis();

        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 30),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 22, 45),
                        LocalDateTime.of(2025, 10, 3, 8, 15),
                        SleepQuality.NORMAL
                )
        );

        SleepAnalysisResult result = analysis.apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }

    @Test
    void shouldReturnDoveWhenChronotypesAreTied() {
        ChronotypeAnalysis analysis = new ChronotypeAnalysis();

        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        SleepQuality.GOOD
                ), // OWL
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 21, 30),
                        LocalDateTime.of(2025, 10, 3, 6, 30),
                        SleepQuality.NORMAL
                ) // LARK
        );

        SleepAnalysisResult result = analysis.apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }

    @Test
    void shouldIgnoreDaySessionsForChronotype() {
        ChronotypeAnalysis analysis = new ChronotypeAnalysis();

        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 14, 0),
                        LocalDateTime.of(2025, 10, 1, 15, 0),
                        SleepQuality.NORMAL
                ), // дневной сон, игнорируется
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        SleepQuality.GOOD
                ) // OWL
        );

        SleepAnalysisResult result = analysis.apply(sessions);

        assertEquals(Chronotype.OWL, result.getValue());
    }
}
