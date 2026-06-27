package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Хронотип пользователя";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        List<SleepingSession> safeSessions = Objects.requireNonNullElse(sessions, List.of());

        Map<Chronotype, Long> counts = safeSessions.stream()
                .filter(session -> session.getNightDate().isPresent())
                .collect(Collectors.groupingBy(
                        SleepingSession::getNightChronotype,
                        Collectors.counting()
                ));

        long owls = counts.getOrDefault(Chronotype.OWL, 0L);
        long larks = counts.getOrDefault(Chronotype.LARK, 0L);
        long doves = counts.getOrDefault(Chronotype.DOVE, 0L);

        Chronotype resultChronotype;
        if (owls > larks && owls > doves) {
            resultChronotype = Chronotype.OWL;
        } else if (larks > owls && larks > doves) {
            resultChronotype = Chronotype.LARK;
        } else {
            resultChronotype = Chronotype.DOVE;
        }

        return new SleepAnalysisResult(DESCRIPTION, resultChronotype);
    }
}