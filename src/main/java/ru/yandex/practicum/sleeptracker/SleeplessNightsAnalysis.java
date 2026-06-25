package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SleeplessNightsAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0L);
        }

        LocalDate startNight = getStartNightDate(sessions);
        LocalDate endNight = getEndNightDate(sessions);

        long totalNights = ChronoUnit.DAYS.between(startNight, endNight) + 1;

        long nightsWithSleep = sessions.stream()
                .map(SleepingSession::getNightDate)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .distinct()
                .count();

        long sleeplessNights = totalNights - nightsWithSleep;

        return new SleepAnalysisResult("Количество бессонных ночей", sleeplessNights);
    }

    private LocalDate getStartNightDate(List<SleepingSession> sessions) {
        SleepingSession firstSession = sessions.get(0);

        if (firstSession.getSleepStart().toLocalTime().isBefore(LocalTime.NOON)) {
            return firstSession.getSleepStart().toLocalDate().minusDays(1);
        }

        return firstSession.getSleepStart().toLocalDate();
    }

    private LocalDate getEndNightDate(List<SleepingSession> sessions) {
        SleepingSession lastSession = sessions.get(sessions.size() - 1);

        Optional<LocalDate> lastNight = lastSession.getNightDate();
        if (lastNight.isPresent()) {
            return lastNight.get();
        }

        if (lastSession.getWakeUp().toLocalTime().isBefore(LocalTime.NOON)) {
            return lastSession.getWakeUp().toLocalDate().minusDays(1);
        }

        return lastSession.getWakeUp().toLocalDate();
    }
}