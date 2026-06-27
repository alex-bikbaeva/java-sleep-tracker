package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class SleepingSession {
    private static final LocalTime NIGHT_START = LocalTime.MIDNIGHT;
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    private static final LocalTime OWL_SLEEP_AFTER = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_AFTER = LocalTime.of(9, 0);

    private static final LocalTime LARK_SLEEP_BEFORE = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_BEFORE = LocalTime.of(7, 0);

    private static final LocalTime EARLY_MORNING_BORDER = LocalTime.of(6, 0);

    private final LocalDateTime sleepStart;
    private final LocalDateTime wakeUp;
    private final SleepQuality quality;

    public SleepingSession(LocalDateTime sleepStart, LocalDateTime wakeUp, SleepQuality quality) {
        this.sleepStart = sleepStart;
        this.wakeUp = wakeUp;
        this.quality = quality;
    }

    public LocalDateTime getSleepStart() {
        return sleepStart;
    }

    public LocalDateTime getWakeUp() {
        return wakeUp;
    }

    public SleepQuality getQuality() {
        return quality;
    }

    public long getDurationInMinutes() {
        return Duration.between(sleepStart, wakeUp).toMinutes();
    }

    public Optional<LocalDate> getNightDate() {
        LocalDate startDate = sleepStart.toLocalDate();
        LocalDate endDate = wakeUp.toLocalDate();

        if (!startDate.equals(endDate)) {
            LocalDateTime nightStart = endDate.atTime(NIGHT_START);
            LocalDateTime nightEnd = endDate.atTime(NIGHT_END);

            if (intersects(sleepStart, wakeUp, nightStart, nightEnd)) {
                return Optional.of(endDate.minusDays(1));
            }
        } else {
            LocalDateTime nightStart = startDate.atTime(NIGHT_START);
            LocalDateTime nightEnd = startDate.atTime(NIGHT_END);

            if (intersects(sleepStart, wakeUp, nightStart, nightEnd)) {
                return Optional.of(startDate.minusDays(1));
            }
        }

        return Optional.empty();
    }

    public Chronotype getNightChronotype() {
        LocalTime sleepTime = sleepStart.toLocalTime();
        LocalTime wakeTime = wakeUp.toLocalTime();

        boolean owlSleepTime = sleepTime.isAfter(OWL_SLEEP_AFTER) || sleepTime.isBefore(EARLY_MORNING_BORDER);
        boolean owlWakeTime = wakeTime.isAfter(OWL_WAKE_AFTER);

        if (owlSleepTime && owlWakeTime) {
            return Chronotype.OWL;
        }

        if (sleepTime.isBefore(LARK_SLEEP_BEFORE) && wakeTime.isBefore(LARK_WAKE_BEFORE)) {
            return Chronotype.LARK;
        }

        return Chronotype.DOVE;
    }

    private boolean intersects(LocalDateTime start1, LocalDateTime end1,
                               LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }
}