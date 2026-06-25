package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MaxDurationAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult>  {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        long maxDuration = sleepingSessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .max()
                .orElse(0L);
        return new SleepAnalysisResult("Максимальная длительность сессии сна (в минутах)", maxDuration);
    }
}
