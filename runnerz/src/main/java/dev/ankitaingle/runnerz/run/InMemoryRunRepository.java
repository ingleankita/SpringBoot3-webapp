package dev.ankitaingle.runnerz.run;

import jakarta.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryRunRepository {
    // Initial in-memory list data structure to store runs

    private List<Run> runs = new ArrayList<>();

    // Get all runs
    List<Run> findAll() {
        return runs;
    }

    // Get run by id
    Optional<Run> findById(Integer id) {
        return runs.stream().filter(run -> run.id() == id).findFirst();
    }

    // Add a new run
    void create(Run run) {
        runs.add(run);
    }

    void update(Run run, Integer id) {
        Optional<Run> existingRun = findById(id);
        if (existingRun.isPresent()) {
            runs.set(runs.indexOf(existingRun.get()), run);
        }
    }

    void delete(Integer id) {
        runs.removeIf(run -> run.id().equals(id));
    }


/*
    // The @PostConstruct annotation is used to mark a method that should be executed after the dependency injection is done to perform any initialization. This method runs only once after the bean's properties have been set.
    @PostConstruct
    private void init() {
        runs.add(new Run(1, "Monday Morning Run", LocalDateTime.now(), LocalDateTime.now().plus(30, ChronoUnit.MINUTES), 3, Location.INDOOR));
        runs.add(new Run(2, "Wednesday Evening Run", LocalDateTime.now(), LocalDateTime.now().plus(60, ChronoUnit.MINUTES), 6, Location.INDOOR));
    }
*/

}
