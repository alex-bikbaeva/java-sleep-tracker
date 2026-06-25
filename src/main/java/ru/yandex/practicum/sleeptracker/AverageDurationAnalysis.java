package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AverageDurationAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        double averageDuration = sleepingSessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .average()
                .orElse(0L);

        return new SleepAnalysisResult("Средняя длительность сессии сна (в минутах)", averageDuration);
    }
}
