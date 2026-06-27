package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class MaxDurationAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Максимальная длительность сессии сна (в минутах)";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        List<SleepingSession> safeSessions = Objects.requireNonNullElse(sessions, List.of());

        long maxDuration = safeSessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .max()
                .orElse(0);

        return new SleepAnalysisResult(DESCRIPTION, maxDuration);
    }
}