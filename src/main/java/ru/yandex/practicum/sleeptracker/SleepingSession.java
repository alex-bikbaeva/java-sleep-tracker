package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class SleepingSession {
    private final LocalDateTime sleepStart;
    private final LocalDateTime wakeUp;
    private final SleepQuality sleepQuality;


    public SleepingSession(LocalDateTime sleepStart, LocalDateTime wakeUp, SleepQuality sleepQuality) {
        this.sleepStart = sleepStart;
        this.wakeUp = wakeUp;
        this.sleepQuality = sleepQuality;
    }

    public long getDurationMinutes() {
        return Duration.between(sleepStart, wakeUp).toMinutes();
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    public Optional<LocalDate> getNightDate() {
        LocalDateTime start = sleepStart;
        LocalDateTime end = wakeUp;

        if (!start.toLocalDate().equals(end.toLocalDate())) {
            return Optional.of(start.toLocalDate());
        }

        if (start.toLocalTime().isBefore(LocalTime.of(6,0))
                && end.toLocalTime().isBefore(LocalTime.of(6,1))) {
            return Optional.of(start.toLocalDate().minusDays(1));
        }

        return Optional.empty();
    }

    public LocalDateTime getWakeUp() {
        return wakeUp;
    }

    public LocalDateTime getSleepStart() {
        return sleepStart;
    }

    public Chronotype getNightChronotype() {
        LocalTime sleepTime = sleepStart.toLocalTime();
        LocalTime wakeTime = wakeUp.toLocalTime();

        if (sleepTime.isAfter(LocalTime.of(23, 0)) && wakeTime.isAfter(LocalTime.of(9, 0))) {
            return Chronotype.OWL;
        }

        if (sleepTime.isBefore(LocalTime.of(22, 0)) && wakeTime.isBefore(LocalTime.of(7, 0))) {
            return Chronotype.LARK;
        }

        return Chronotype.DOVE;
    }
}
