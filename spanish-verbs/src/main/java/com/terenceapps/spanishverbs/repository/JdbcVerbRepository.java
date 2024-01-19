package com.terenceapps.spanishverbs.repository;

import com.terenceapps.spanishverbs.model.Verb;
import com.terenceapps.spanishverbs.model.VerbConjugated;
import com.terenceapps.spanishverbs.repository.util.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Repository
public class JdbcVerbRepository implements VerbRepository {

    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = Logger.getLogger(JdbcVerbRepository.class.getName());


    public JdbcVerbRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(String infinitive, String mood, String tense, BigDecimal userId) {
        String sql = "INSERT INTO saved (infinitive, mood, tense, userid) VALUES (?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql, infinitive, mood, tense, userId);
        } catch (Exception e) {
            logger.info("Failed to save verb: " + e);
        }
    }


    @Override
    public void unsave(String infinitive, String mood, String tense, BigDecimal userId) {
        String sql = "DELETE FROM saved WHERE infinitive=? AND mood=? AND tense=? AND userid=?";
        try {
            jdbcTemplate.update(sql, infinitive, mood, tense, userId);
        } catch (Exception e) {
            logger.info("Failed to delete verb: " + e);
        }

    }


    @Override
    public Optional<List<Verb>> findAllSaved(BigDecimal userId) {
        String sql = "SELECT * FROM saved WHERE userid=?";

        List<Verb> resultSet = jdbcTemplate.query(sql,
                (row, index) -> new Verb(row.getString("infinitive"), row.getString("mood"), row.getString("tense")), userId);
        return Optional.of(resultSet);
    }


    @Override
    public Optional<VerbConjugated> findByCompositeKey(String infinitive, String mood, String tense) {
        String sql = "SELECT * FROM verbs WHERE infinitive=? AND mood=? AND tense=?";

        List<VerbConjugated> verbConjugated = jdbcTemplate.query(sql, new RowMapper(), infinitive, mood, tense);

        return Optional.of(verbConjugated.get(0));
    }

    @Override
    public Optional<VerbConjugated> findNonSaved(BigDecimal userId) {
        String sql = "SELECT * FROM verbs WHERE NOT EXISTS " +
                "(SELECT * FROM saved WHERE verbs.infinitive = saved.infinitive AND verbs.mood = saved.mood AND verbs.tense = saved.tense AND userid=?) LIMIT 1";

        List<VerbConjugated> verbs = jdbcTemplate.query(sql, new RowMapper(), userId);

        if (verbs.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(verbs.get(0));
    }
}
