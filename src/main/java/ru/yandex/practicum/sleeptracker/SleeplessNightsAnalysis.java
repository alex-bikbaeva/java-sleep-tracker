package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class SleeplessNightsAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Количество бессонных ночей";
    private static final LocalTime NOON_BORDER = LocalTime.NOON;

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        List<SleepingSession> safeSessions = Objects.requireNonNullElse(sessions, List.of());

        if (safeSessions.isEmpty()) {
            return new SleepAnalysisResult(DESCRIPTION, 0L);
        }

        LocalDate startNight = getStartNightDate(safeSessions);
        LocalDate endNight = getEndNightDate(safeSessions);

        long totalNights = ChronoUnit.DAYS.between(startNight, endNight) + 1;

        long nightsWithSleep = safeSessions.stream()
                .map(SleepingSession::getNightDate)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .distinct()
                .count();

        long sleeplessNights = totalNights - nightsWithSleep;

        return new SleepAnalysisResult(DESCRIPTION, sleeplessNights);
    }

    private LocalDate getStartNightDate(List<SleepingSession> sessions) {
        SleepingSession firstSession = sessions.get(0);

        if (firstSession.getSleepStart().toLocalTime().isBefore(NOON_BORDER)) {
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

        if (lastSession.getWakeUp().toLocalTime().isBefore(NOON_BORDER)) {
            return lastSession.getWakeUp().toLocalDate().minusDays(1);
        }

        return lastSession.getWakeUp().toLocalDate();
    }
}