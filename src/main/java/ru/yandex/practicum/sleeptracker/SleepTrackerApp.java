package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static final List<Function<List<SleepingSession>, SleepAnalysisResult>> ANALYSES = List.of(
            new CountSessionsAnalysis(),
            new MinDurationAnalysis(),
            new MaxDurationAnalysis(),
            new AverageDurationAnalysis(),
            new BadQualityCountAnalysis(),
            new SleeplessNightsAnalysis(),
            new ChronotypeAnalysis()
    );

    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            System.out.println("Не указан путь к файлу с логом сна");
            return;
        }

        String filename = args[0];

        try {
            List<SleepingSession> sleepingSessions = Files.lines(Path.of(filename))
                    .map(SleepTrackerApp::parseSession)
                    .toList();

            ANALYSES.stream()
                    .map(analysis -> analysis.apply(sleepingSessions))
                    .forEach(result -> System.out.println(result.getDescription() + ": " + result.getValue()));
        } catch (IOException exception) {
            System.out.println("Возникла ошибка при чтении файла: " + exception.getMessage());
        }

    }

    private static SleepingSession parseSession(String line) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        String[] parts = line.split(";");
        LocalDateTime startSleep = LocalDateTime.parse(parts[0], formatter);
        LocalDateTime wakeUp = LocalDateTime.parse(parts[1], formatter);
        SleepQuality sleepQuality = SleepQuality.valueOf(parts[2]);

        return new SleepingSession(startSleep, wakeUp, sleepQuality);
    }
}