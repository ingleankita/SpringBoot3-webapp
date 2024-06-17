package dev.ankitaingle.runnerz.run;

import dev.ankitaingle.runnerz.Application;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* The @Repository annotation is used to indicate that a class is a Data Access Object (DAO), which will handle database operations */
@Repository
public class RunRepository {

    private static final Logger log = LoggerFactory.getLogger(RunRepository.class);
    private final JdbcClient jdbcClient;

    public RunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // In-memory database to store runs

    public List<Run> findAll() {
        return jdbcClient.sql("SELECT * FROM Run").query(Run.class).list();
    }

    public Optional<Run> findById(Integer id) {
        return jdbcClient.sql("SELECT id, title, started_on, completed_on, miles, location FROM Run WHERE Run.id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        var updated = jdbcClient.sql("INSERT INTO Run(id, title, started_on, completed_on, miles, location) VALUES (?,?,?,?,?,?);")
                .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
                .update();
        Assert.state(updated == 1, "Failed to create run " + run.title());
    }

    public void update(Run run, Integer id) {
        var updated = jdbcClient.sql("UPDATE Run SET title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? WHERE id = ?;")
                .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id))
                .update();
        Assert.state(updated == 1, "Failed to update run " + run.title());
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM Run WHERE id = :id;")
                .param("id", id)
                .update();
        Assert.state(updated == 1, "Failed to delete run " + id);
    }

/*  // Initial in-memory list data structure to store runs

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
     The @PostConstruct annotation is used to mark a method that should be executed after the dependency injection is
     done to perform any initialization. This method runs only once after the bean's properties have been set.
    * /
    @PostConstruct
    private void init() {
        runs.add(new Run(1, "Monday Morning Run", LocalDateTime.now(), LocalDateTime.now().plus(30, ChronoUnit.MINUTES), 3, Location.INDOOR));
        runs.add(new Run(2, "Wednesday Evening Run", LocalDateTime.now(), LocalDateTime.now().plus(60, ChronoUnit.MINUTES), 6, Location.INDOOR));
    }
*/
}
