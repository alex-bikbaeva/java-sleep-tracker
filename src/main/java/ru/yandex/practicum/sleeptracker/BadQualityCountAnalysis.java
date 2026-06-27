package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class BadQualityCountAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Количество сессий с плохим качеством сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        List<SleepingSession> safeSessions = Objects.requireNonNullElse(sessions, List.of());

        long badCount = safeSessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();

        return new SleepAnalysisResult(DESCRIPTION, badCount);
    }
}