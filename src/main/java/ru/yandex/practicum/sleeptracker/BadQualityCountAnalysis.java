package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadQualityCountAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        long badCount = sleepingSessions.stream()
                .filter(sleepingSession -> sleepingSession.getSleepQuality() == SleepQuality.BAD)
                .count();
        return new SleepAnalysisResult("Количество сессий с плохим качеством сна", badCount);

    }
}
