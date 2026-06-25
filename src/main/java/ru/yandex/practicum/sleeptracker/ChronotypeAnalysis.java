package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        List<SleepingSession> nightSessions = sessions.stream()
                .filter(session -> session.getNightDate().isPresent())
                .collect(Collectors.toList());

        long owls = nightSessions.stream()
                .filter(session -> session.getNightChronotype() == Chronotype.OWL)
                .count();

        long larks = nightSessions.stream()
                .filter(session -> session.getNightChronotype() == Chronotype.LARK)
                .count();

        long doves = nightSessions.stream()
                .filter(session -> session.getNightChronotype() == Chronotype.DOVE)
                .count();

        Chronotype resultChronotype;
        if (owls > larks && owls > doves) {
            resultChronotype = Chronotype.OWL;
        } else if (larks > owls && larks > doves) {
            resultChronotype = Chronotype.LARK;
        } else {
            resultChronotype = Chronotype.DOVE;
        }

        return new SleepAnalysisResult("Хронотип пользователя", resultChronotype);
    }
}