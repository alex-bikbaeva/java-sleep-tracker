package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class AverageDurationAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Средняя длительность сессии сна (в минутах)";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        List<SleepingSession> safeSessions = Objects.requireNonNullElse(sessions, List.of());

        double averageDuration = safeSessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .average()
                .orElse(0);

        return new SleepAnalysisResult(DESCRIPTION, averageDuration);
    }
}