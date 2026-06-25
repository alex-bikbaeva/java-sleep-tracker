package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MinDurationAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        long minDuration = sleepingSessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .min()
                .orElse(0L);
        return new SleepAnalysisResult("Минимальная длительность сессии сна (в минутах)", minDuration);
    }
}
